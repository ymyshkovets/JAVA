package apiTests;

import controller.UserController;
import io.restassured.response.Response;
import models.ApiResponse;
import models.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static constants.ConstantsApi.DEFAULT_USER;
import static constants.ConstantsApi.expectedUser;
import static io.restassured.RestAssured.given;

class UserTests {

    private static final String BASE_URL = "https://petstore.swagger.io";

    @Test
    void createUserTest() {
        String body = """
                {
                    "id": 0,
                    "username": "string",
                    "firstName": "string",
                    "lastName": "string",
                    "email": "string",
                    "password": "string",
                    "phone": "string",
                    "userStatus": 0
                }
                """;
        given().
                baseUri(BASE_URL).
                //header("accept", "application/json").
                //header("Content-Type", "application/json").
                accept("application/json").
                contentType("application/json").
                body(body).
                log().all().
                when().
                post("/v2/user").
                then().
                statusCode(200).
                log().all().
                body("code", Matchers.equalTo(200),
                        "type", Matchers.equalTo("unknown"),
                        //"message", Matchers.greaterThan("9223372036854768470")
);
    }

    @Test
    void updateUserTest() {
        String username = "string2";
        String body = """
                {
                    "id": 1,
                    "username": "string2",
                    "firstName": "string2",
                    "lastName": "string2",
                    "email": "string2",
                    "password": "string2",
                    "phone": "string2",
                    "userStatus": 1
                }
                """;
        given().
                baseUri(BASE_URL).
                accept("application/json").
                contentType("application/json").
                body(body).
                log().all().
                when().
                put("/v2/user/" + username).
                then().
                statusCode(200).
                log().all().
                body("code", Matchers.equalTo(200),
                        "type", Matchers.equalTo("unknown"),
                        "message", Matchers.equalTo("1"));
    }

    @Test
    void getUserTest() {
        UserController userController = new UserController();

        Response response = userController.getUser("string");

        Assertions.assertEquals(200, response.getStatusCode());
        User user = response.as(User.class);
        Assertions.assertTrue(user.getId() > 9223372036854767242L);
        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    void createUserWithSerializationTest() {
        UserController userController = new UserController();

        Response response = userController.createUser(DEFAULT_USER);

        Assertions.assertEquals(200, response.getStatusCode());
        ApiResponse user = response.as(ApiResponse.class);
        Assertions.assertEquals(200, user.getCode());
        Assertions.assertEquals("unknown", user.getType());
        Assertions.assertTrue(Long.parseLong(user.getMessage()) > 9223372036854768470L);
    }

    @Test
    void deleteUserTest() {
        //
    }
}
