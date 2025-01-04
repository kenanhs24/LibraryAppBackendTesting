package com.LibraryApp.step_definitions;

import com.LibraryApp.utilities.ConfigurationReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.sql.*;
import java.util.List;

/**
 * This class demonstrates how you could tie together:
 *  - UI login (Selenium)
 *  - API calls (RestAssured)
 *  - DB queries (JDBC)
 * using the credentials you've posted. Adjust to fit your actual framework.
 */
public class Api_Step_Definition {

    // ------------------------------------------------
    // Shared fields to store data across steps:
    // ------------------------------------------------
    private String token;          // from API login
    private String newBookId;      // example: store newly created book_id
    private String newUserId;      // example: store newly created user_id
    private String actualStatus;   // store API response status code or DB verification results
    private ResultSet resultSet;   // DB query results, if needed

    // ------------------------------------------------
    // UI STEPS
    // ------------------------------------------------

    @Given("I logged in Library UI as {string}")
    public void i_logged_in_library_ui_as(String role) {
        /*
           Typically, you’d do:
             1. Launch browser
             2. Go to base_url
             3. Enter correct credentials for given role
             4. Click login
        */
        System.out.println("Launching browser: " + ConfigurationReader.get("browser"));
        String url = ConfigurationReader.get("base_url"); // "https://library2.cydeo.com/login.html"
        System.out.println("Navigating to " + url);

        // For demonstration, we’ll just print.
        // In a real project, you might do:
        //     Driver.getDriver().get(url);
        //     new LoginPage().login(username, password);

        String username = "";
        String password = "";
        if ("librarian".equalsIgnoreCase(role)) {
            username = ConfigurationReader.get("librarian.email");   //  librarian10@library
            password = ConfigurationReader.get("librarian.password"); // libraryUser
        } else if ("student".equalsIgnoreCase(role)) {
            username = ConfigurationReader.get("student.email");     // student5@library
            password = ConfigurationReader.get("student.password");   // libraryUser
        }

        // Just printing out. Normally you'd type these into a login form.
        System.out.println("UI Login as: " + username + " / " + password);
        // loginPage.userNameField.sendKeys(username);
        //      loginPage.passwordField.sendKeys(password);
        //      loginPage.signInBtn.click();
    }

    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String pageName) {
        // For example, from your UI test, if you need to click on "Books" or "Users" modules, etc.
        System.out.println("Navigating to " + pageName + " page in the UI...");
        // e.g. new DashboardPage().navigateModule("Books");
    }

    // ------------------------------------------------
    // API STEPS
    // ------------------------------------------------

    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String role) {
        /*
            1) Determine username/password from role
            2) Use RestAssured or a similar library to POST a login request, get token
            3) Store token in a field
         */
        String username = "";
        String password = "";
        if ("librarian".equalsIgnoreCase(role)) {
            username = ConfigurationReader.get("librarian.email");
            password = ConfigurationReader.get("librarian.password");
        } else if ("student".equalsIgnoreCase(role)) {
            username = ConfigurationReader.get("student.email");
            password = ConfigurationReader.get("student.password");
        }
        System.out.println("Logging in Library API as: " + username + " / " + password);

        // Example pseudo-code using RestAssured:
        /*
        Response response =
            given()
                .baseUri(ConfigurationReader.get("library.base_url"))  //  https://library2.cydeo.com/rest/v1
                .formParam("email", username)
                .formParam("password", password)
            .when()
                .post("/login");

        token = response.jsonPath().getString("token");
        System.out.println("Received token: " + token);
        */
    }

    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentType) {
        // Typically, you'd configure your RestAssured request spec:
        System.out.println("Setting Content-Type header to: " + contentType);
        /*
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(ConfigurationReader.get("library.base_url"))
                .addHeader("x-library-token", token)
                .addHeader("Content-Type", contentType)
                .build();
        */
    }

    @Given("Accept header is {string}")
    public void accept_header_is(String acceptType) {
        System.out.println("Setting Accept header to: " + acceptType);
        // Similar to above, you might do:
        // requestSpecification.header("Accept", acceptType);
    }

    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String objectType) {
        // You might create a random Book, or a random User, etc.
        System.out.println("Creating random " + objectType + " data for the request body.");
        // e.g. create a map or POJO with random values, store in some field to send later
    }

    @Given("I send token information as request body")
    public void i_send_token_information_as_request_body() {
        // If needed, you’d attach token in the form body or JSON body
        System.out.println("Attaching token info in the request body");
    }

    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
        System.out.println("Sending GET request to: " + endpoint);
        //
        /*
        response = given()
                .spec(requestSpecification)
            .when()
                .get(endpoint);

        System.out.println("Response: " + response.prettyPrint());
        */
    }

    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endpoint) {
        System.out.println("Sending POST request to: " + endpoint);
        //
        /*
        response = given()
                .spec(requestSpecification)
                .formParam("name", "Random Book Title") // or body(object) if JSON
                .formParam("isbn", ... )
                . ...
            .when()
                .post(endpoint);
        newBookId = response.jsonPath().getString("book_id");
        System.out.println("New Book ID: " + newBookId);
        */
    }

    @Then("status code should be {int}")
    public void status_code_should_be(Integer expectedStatus) {
        // In real code: Assert.assertEquals(expectedStatus.intValue(), response.statusCode());
        System.out.println("Asserting response status code == " + expectedStatus);
    }

    @Then("Response Content type is {string}")
    public void response_content_type_is(String expectedContentType) {
        // In real code: Assert.assertEquals(expectedContentType, response.contentType());
        System.out.println("Asserting content type == " + expectedContentType);
    }

    @Then("{string} field should not be null")
    public void field_should_not_be_null(String fieldName) {
        // In real code: Object value = response.jsonPath().get(fieldName);
        // Assert.assertNotNull("Field " + fieldName + " was null!", value);
        System.out.println("Asserting field " + fieldName + " is not null.");
    }

    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String fieldName) {
        // If you stored your path param earlier, compare with JSON field
        System.out.println("Asserting " + fieldName + " equals the previously saved path param.");
    }

    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(DataTable dataTable) {
        // dataTable contains a list of fields
        List<String> fields = dataTable.asList();
        for (String field : fields) {
            // In real code:
            //   Object value = response.jsonPath().get(field);
            //   Assert.assertNotNull("Field " + field + " was null!", value);
            System.out.println("Checking field not null: " + field);
        }
    }

    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String fieldName, String expectedValue) {
        // In real code:
        //   String actualValue = response.jsonPath().getString(fieldName);
        //   Assert.assertEquals(expectedValue, actualValue);
        System.out.println("Asserting " + fieldName + " == " + expectedValue);
    }

    @Then("“user_id\" field should not be null")
    public void user_id_field_should_not_be_null() {
        // Example for a user_id
        System.out.println("Asserting user_id field is not null.");
    }

    // ------------------------------------------------
    // DB STEPS
    // ------------------------------------------------

    @Then("I connect to library2 DB and verify newly created {string} in the database")
    public void i_connect_to_library2_db_and_verify_newly_created_in_the_database(String objectType) {
        /*
            1) Create DB connection using your credentials
            2) Query your DB to verify the newly inserted record
            3) Compare with your test data
         */
        String url = ConfigurationReader.get("library2.db.url");         // e.g. jdbc:mysql://34.230.35.214:3306/library2
        String dbUsername = ConfigurationReader.get("library2.db.username"); // e.g. library2_client
        String dbPassword = ConfigurationReader.get("library2.db.password"); // e.g. 6s2LQQTjBcGFfDhY

        System.out.println("Connecting to DB: " + url);

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {

            String query = "";
            if ("book".equalsIgnoreCase(objectType)) {
                // Suppose newBookId was captured from the API response
                query = "SELECT * FROM books WHERE id = " + newBookId;
            } else if ("user".equalsIgnoreCase(objectType)) {
                // Suppose newUserId was captured from the API response
                query = "SELECT * FROM users WHERE id = " + newUserId;
            }
            System.out.println("Executing query: " + query);
            resultSet = statement.executeQuery(query);

            // Simple check if the record was found
            if (!resultSet.next()) {
                Assert.fail(objectType + " with given ID was NOT found in the DB!");
            } else {
                System.out.println(objectType + " with ID found in DB. Verification can continue...");
                // e.g., verify name, email, etc. match
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("DB connection or query failed!");
        }
    }

    // ------------------------------------------------
    // Cross-layer verification step
    // ------------------------------------------------
    @Then("UI, Database and API created book information must match")
    public void ui_database_and_api_created_book_information_must_match() {
        /*
          This step suggests you:
            1) Compare the newly created book from the API response
            2) Compare the same record in the UI
            3) Compare it in the DB
            4) Ensure they match

          You might do something like:
            - from API: "Book Title", "ISBN", etc.
            - from UI: read actual text from some Book page
            - from DB: query columns in the books table
            - then Assert everything is consistent
         */
        System.out.println("Verifying newly created book matches across API, UI, and DB...");
        // Example:
        // String apiBookTitle = apiResponse.jsonPath().getString("title");
        // String uiBookTitle = new BooksPage().getBookTitle(newBookId);
        // String dbBookTitle = ...
        // Assert.assertEquals(apiBookTitle, uiBookTitle);
        // Assert.assertEquals(uiBookTitle, dbBookTitle);
    }

        /**
         * Step definition for:
         *   Given Path param is "1"
         */
        @Given("Path param is {string}")
        public void path_param_is(String paramValue) {
            // TODO: Store the path param value so you can use it when sending the request.
            // For example, you might keep it in a scenario context or static variable.
            System.out.println("Storing path param: " + paramValue);

            // e.g.,
            // ScenarioContext.set("pathParam", paramValue);
        }

        /**
         * Step definition for:
         *   Given I logged Library api with credentials "student5@library" and "libraryUser"
         *   (or other user/password combos)
         */
        @Given("I logged Library api with credentials {string} and {string}")
        public void i_logged_library_api_with_credentials_and(String email, String password) {
            // TODO: Perform the actual login logic with RestAssured (or whichever library you're using)
            //       Then store the token or session info.
            System.out.println("Logging in with email: " + email + ", password: " + password);

            // Example pseudo-code:
        /*
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .post(ConfigurationReader.getProperty("library.base_url") + "/login");

        if (response.statusCode() != 200) {
            throw new RuntimeException("Login failed! Status: " + response.statusCode());
        }

        // store the token:
        String token = "Bearer " + response.jsonPath().getString("token");
        ScenarioContext.set("token", token);
        */
        }
    }

