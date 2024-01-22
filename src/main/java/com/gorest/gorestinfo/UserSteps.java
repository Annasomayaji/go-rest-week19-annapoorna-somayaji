package com.gorest.gorestinfo;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class UserSteps {

    @Step("Creating user with name : {0}, email : {1} , gender : {2} , status : {3}")
    public ValidatableResponse createUser(String name, String email, String gender, String status) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .header("Content-Type", "application/json")
                .when()
                .body(UserPojo.getUserPojo(name, email, gender, status))
                .post(EndPoints.CREATE_USER)
                .then().log().all();

    }

    @Step("Getting all users ")
    public HashMap<String, Object> getAllUsers(String name) {
        ValidatableResponse response;
        response = SerenityRest.given()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .when()
                .get(EndPoints.GET_ALL_USER)
                .then().log().all();
        HashMap<String, Object> map = response.extract().path("findAll{it.name== '" + name + "'}.get(0)");
        return map;
    }


    @Step("Getting user by id : {0}")
    public ValidatableResponse getUserById(int id) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .pathParam("ID", id)
                .when()
                .get(EndPoints.USER_BY_ID)
                .then().log().all();
    }

    @Step("Updating user with id: {0}, name : {1}, email : {2} , gender : {3} , status : {4} ")
    public ValidatableResponse updateUserById(int id, String name, String email, String gender, String status) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .header("Content-Type", "application/json")
                .pathParam("ID", id)
                .when()
                .body(UserPojo.getUserPojo(name, email, gender, status))
                .put(EndPoints.USER_BY_ID)
                .then().log().all();

    }

    @Step("Deleting user with id : {0}")
    public ValidatableResponse deleteUserById(int id){
     return   SerenityRest.given()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
             .header("Content-Type", "application/json")
                .pathParam("ID", id)
                .when()
                .delete(EndPoints.USER_BY_ID)
                .then().log().all();
    }

}
