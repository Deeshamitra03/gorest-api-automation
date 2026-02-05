package com.company.gorest.tests;

import com.company.gorest.base.BaseTest;
import com.company.gorest.client.UserApiClient;
import com.company.gorest.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserCrudTest extends BaseTest {

    private UserApiClient userApiClient = new UserApiClient();

    // Crucial: We store the ID here so all tests can share it (Request Chaining)
    private static int userId;

    // STEP 1: Create User
    @Test(priority = 1)
    public void testCreateUser() {
        // 1. Prepare data (Random email to avoid "Email already taken" error)
        String randomEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";
        User newUser = new User("Deesha Mitra", randomEmail, "female", "active");

        // 2. Send Request
        Response response = userApiClient.createUser(requestSpec, newUser);

        // 3. Validations (per Assignment)
        Assert.assertEquals(response.getStatusCode(), 201, "Status should be 201 Created");

        // 4. Extract ID for next steps (The "Chaining" part)
        userId = response.jsonPath().getInt("id");
        System.out.println("Created User ID: " + userId);

        // Validate other fields
        Assert.assertEquals(response.jsonPath().getString("name"), "Deesha Mitra");
        Assert.assertEquals(response.jsonPath().getString("email"), randomEmail);
    }

    // STEP 2: Get User
    @Test(priority = 2)
    public void testGetUser() {
        Response response = userApiClient.getUser(requestSpec, userId);

        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 OK");
        Assert.assertEquals(response.jsonPath().getInt("id"), userId);
        Assert.assertEquals(response.jsonPath().getString("name"), "Deesha Mitra");
    }

    // STEP 3: Update User
    @Test(priority = 3)
    public void testUpdateUser() {
        // Change name to "Deesha Updated"
        User updateUser = new User("Deesha Updated", "new" + System.currentTimeMillis() + "@gmail.com", "female", "active");

        Response response = userApiClient.updateUser(requestSpec, userId, updateUser);

        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 OK");
        Assert.assertEquals(response.jsonPath().getString("name"), "Deesha Updated");
    }

    // STEP 4: Delete User
    @Test(priority = 4)
    public void testDeleteUser() {
        Response response = userApiClient.deleteUser(requestSpec, userId);

        Assert.assertEquals(response.getStatusCode(), 204, "Status should be 204 No Content");
    }

    // STEP 5: Verify Deletion (Negative Test)
    @Test(priority = 5)
    public void testVerifyDeletion() {
        Response response = userApiClient.getUser(requestSpec, userId);

        Assert.assertEquals(response.getStatusCode(), 404, "User should not be found (404)");
    }
}