package com.LibraryApp.pages;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseApi
{
    public static RequestSpecification getRequestSpec(String baseURI) {
        RestAssured.baseURI = baseURI;
        return RestAssured.given()
                .header("Accept", "application/json")
                .contentType("application/json");
    }
}
