package com.gorest.crudtest;

import com.gorest.gorestinfo.UserSteps;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {

    static String name = "Annu";
    static String email = TestUtils.getRandomValue() + "annusom@gmail.com";
    ;
    static String gender = "female";
    static String status = "active";

    static ValidatableResponse response;
    static int userId;
    @Steps
    UserSteps steps;

//    @Test
//    public void dryRun() {
//
//    }

    @Title("This will create a user")
    @Test
    public void test_001() {
        response = steps.createUser(name, email, gender, status);
        response.statusCode(201);
        userId = response.extract().path("id");

    }

    @Title("This will get the record matching the user name")
    @Test
    public void test_002() {
        HashMap<String, Object> userDetails = steps.getAllUsers(name);
        Assert.assertThat(userDetails, hasValue(name));
    }

    @Title("This will get the user with id")
    @Test
    public void test_003() {
        response = steps.getUserById(userId);
        response.statusCode(200);

    }

    @Title("This will update the user and verify the update")
    @Test
    public void test_004() {
        name = name + "_updated";
        response = steps.updateUserById(userId, name, email, gender, status);
        response.statusCode(200);
        HashMap<String, Object> userDetails = steps.getAllUsers(name);
        Assert.assertThat(userDetails, hasValue(name));
    }

    @Title("This will delete the user and verify deletion")
    @Test
    public void test_005() {
        response = steps.deleteUserById(userId);
        response.statusCode(204);
        response = steps.getUserById(userId);
        response.statusCode(404);
        response.body("$", hasValue("Resource not found"));
    }

}
