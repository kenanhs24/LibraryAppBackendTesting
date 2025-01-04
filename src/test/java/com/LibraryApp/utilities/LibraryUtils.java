package com.LibraryApp.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class LibraryUtils {

    private static final String BASE_URL = "https://library2.cydeo.com/rest/v1";

    /**
     * Instead of storing tokens by email, we store them by ROLE ("librarian", "student").
     * So TOKENS.get("librarian") => returns the librarian's token.
     */
    private static final Map<String, String> TOKENS = new HashMap<>();

    /**
     * Logs in as a specific role and retrieves the token.
     *
     * @param role The role to log in as ("librarian" or "student").
     * @return The authentication token in the format "Bearer <token>".
     */
    public static String loginAs(String role) {
        // If we already have a token for this role in the map, reuse it:
        if (TOKENS.containsKey(role.toLowerCase())) {
            return TOKENS.get(role.toLowerCase());
        }

        // Otherwise, get credentials from configuration.properties
        String email;
        String password;

        switch (role.toLowerCase()) {
            case "librarian":
                email = ConfigurationReader.get("librarian.email");
                password = ConfigurationReader.get("librarian.password");
                break;
            case "student":
                email = ConfigurationReader.get("student.email");
                password = ConfigurationReader.get("student.password");
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        // Now request a token from the API
        String token = requestToken(email, password);

        // Cache it so subsequent calls for the same role do not re-login
        TOKENS.put(role.toLowerCase(), token);

        return token;
    }

    /**
     * Sends a form-urlencoded login request to get the token for given credentials.
     *
     * @param email    The user email.
     * @param password The user password.
     * @return The token in "Bearer <token>" format.
     */
    private static String requestToken(String email, String password) {
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .post(BASE_URL + "/login");

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to log in. Status code: " + response.statusCode() +
                    ", Response: " + response.body().asString());
        }

        String rawToken = response.jsonPath().getString("token");
        // Some APIs require "Bearer " prefix; if yours doesn't, remove "Bearer ".
        return "Bearer " + rawToken;
    }

    /**
     * Ensures a valid token is cached for the given role.
     * If no token exists yet, logs in automatically.
     *
     * @param role The role ("librarian" or "student").
     */
    public static void ensureValidTokenForRole(String role) {
        // If we don't already have a token for the role, do a login
        if (!TOKENS.containsKey(role.toLowerCase())) {
            loginAs(role);
        }
    }

    /**
     * Ensures the passed-in token is not null or empty.
     *
     * @param token The token to validate.
     */
    public static void ensureValidToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token is not initialized or empty. Please log in first.");
        }
    }

    /**
     * Sends a GET request to the specified endpoint, including the given token.
     *
     * @param endpoint The API endpoint (e.g. "/get_all_users").
     * @param token    The auth token, e.g. "Bearer <token>".
     * @return The API response.
     */
    public static Response sendGETRequest(String endpoint, String token) {
        ensureValidToken(token);

        return RestAssured.given()
                .header("Authorization", token)
                .get(BASE_URL + endpoint);
    }

    /**
     * Sends a POST request (JSON body) to the specified endpoint.
     *
     * @param endpoint The API endpoint (e.g. "/add_book").
     * @param body     A map representing the JSON body.
     * @param token    The auth token, e.g. "Bearer <token>".
     * @return The API response.
     */
    public static Response sendPOSTRequest(String endpoint, Map<String, Object> body, String token) {
        ensureValidToken(token);

        return RestAssured.given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(body)
                .post(BASE_URL + endpoint);
    }
}
