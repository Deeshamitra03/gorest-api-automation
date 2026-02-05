package com.company.gorest.base;

import com.company.gorest.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        String baseUrl = ConfigReader.getBaseUrl();
        String token = ConfigReader.getAuthToken();

        RestAssured.baseURI = baseUrl;

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + token)
                .setContentType(ContentType.JSON)
                .build();
    }
}