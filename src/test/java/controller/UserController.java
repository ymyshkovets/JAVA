package controller;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static constants.ConstantsApi.BASE_URL;
import static io.restassured.RestAssured.given;

public class UserController {

    RequestSpecification requestSpecification = given();

    public UserController() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.accept("application/json");
        requestSpecification.contentType("application/json");
        requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Create user")
    public Response createUser(User user) {
        requestSpecification.body(user);
        return given(requestSpecification).when().post("/v2/user");
    }

    @Step("Update user")
    public Response updateUser(String body) {
        requestSpecification.body(body);
        return given(requestSpecification).when().put("/v2/user/" + body);
    }

    @Step("Get user by username")
    public Response getUser(String username) {
        return given(requestSpecification).when().get("/v2/user/" + username);
    }
}
