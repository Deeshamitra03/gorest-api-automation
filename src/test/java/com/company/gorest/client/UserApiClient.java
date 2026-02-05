package com.company.gorest.client;

import com.company.gorest.models.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    public Response createUser(RequestSpecification spec, User user) {
        return given()
                .spec(spec)
                .body(user)
                .when()
                .post("/public/v2/users");
    }


    public Response getUser(RequestSpecification spec, int userId) {
        return given()
                .spec(spec)
                .pathParam("userId", userId)
                .when()
                .get("/public/v2/users/{userId}");
    }


    public Response updateUser(RequestSpecification spec, int userId, User user) {
        return given()
                .spec(spec)
                .body(user)
                .pathParam("userId", userId)
                .when()
                .put("/public/v2/users/{userId}");
    }


    public Response deleteUser(RequestSpecification spec, int userId) {
        return given()
                .spec(spec)
                .pathParam("userId", userId)
                .when()
                .delete("/public/v2/users/{userId}");
    }
}