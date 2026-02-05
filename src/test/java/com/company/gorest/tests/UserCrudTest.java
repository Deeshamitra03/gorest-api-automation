package com.company.gorest.tests;

import com.company.gorest.base.BaseTest;
import com.company.gorest.client.UserApiClient;
import com.company.gorest.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserCrudTest extends BaseTest {

    private UserApiClient userApiClient = new UserApiClient();

    private static int userId;

    @Test(priority = 1)
    public void testCreateUser() {

        String randomEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";
        User newUser = new User("Deesha Mitra", randomEmail, "female", "active");

        Response response = userApiClient.createUser(requestSpec, newUser);


        Assert.assertEquals(response.getStatusCode(), 201, "Status should be 201 Created");


        userId = response.jsonPath().getInt("id");
        System.out.println("Created User ID: " + userId);


        Assert.assertEquals(response.jsonPath().getString("name"), "Deesha Mitra");
        Assert.assertEquals(response.jsonPath().getString("email"), randomEmail);
    }


    @Test(priority = 2)
    public void testGetUser() {
        Response response = userApiClient.getUser(requestSpec, userId);

        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 OK");
        Assert.assertEquals(response.jsonPath().getInt("id"), userId);
        Assert.assertEquals(response.jsonPath().getString("name"), "Deesha Mitra");
    }


    @Test(priority = 3)
    public void testUpdateUser() {

        User updateUser = new User("Deesha Updated", "new" + System.currentTimeMillis() + "@gmail.com", "female", "active");

        Response response = userApiClient.updateUser(requestSpec, userId, updateUser);

        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 OK");
        Assert.assertEquals(response.jsonPath().getString("name"), "Deesha Updated");
    }


    @Test(priority = 4)
    public void testDeleteUser() {
        Response response = userApiClient.deleteUser(requestSpec, userId);

        Assert.assertEquals(response.getStatusCode(), 204, "Status should be 204 No Content");
    }


    @Test(priority = 5)
    public void testVerifyDeletion() {
        Response response = userApiClient.getUser(requestSpec, userId);

        Assert.assertEquals(response.getStatusCode(), 404, "User should not be found (404)");
    }
}