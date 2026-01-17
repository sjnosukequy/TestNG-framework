package org.QAHexagon.testng.api.authAPI;

import org.QAHexagon.testng.api.baseAPI.baseAPI;
// import org.QAHexagon.testng.env.envManager;
import org.QAHexagon.testng.env.tokenManager;

import com.beust.ah.A;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import io.qameta.allure.Allure;

public class loginAPI extends baseAPI {

    static public Response login(String email, String password, boolean setCookie) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .extract()
                .response();

        // set cookies, tokens, etc... from response if needed
        if (setCookie)
            tokenManager.setRefreshToken(response.getCookie("refreshToken"));

        // JsonPath result = response.jsonPath();
        // tokenManager.setAccessToken(result.getString("result.accessToken"));

        return response;
    }

    static public Response refresh() {
        Response response = given()
                .when()
                .cookie("refreshToken", tokenManager.getRefreshToken())
                .post("/api/v1/auth/refresh")
                .then()
                .extract()
                .response();
        // set cookies, tokens, etc... from response if needed
        // JsonPath result = response.jsonPath();
        return response;
    }

    static public Response test() {
        Response response = given()
                .when()
                .get("/posts/1")
                .then()
                .extract()
                .response();

        return response;
    }

    
    static public Response param(Object userId, Object id) {
        Map<String, Object> payload = new HashMap<>();
        notEmptyAddParam(payload, "userId", userId);
        notEmptyAddParam(payload, "id", id);

        Allure.addAttachment("Query Parameters", payload.toString());

        Response response = given()
                .params(payload)
                .when()
                .get("/posts")
                .then()
                .extract()
                .response();

        return response;
    }

    static public Response error() {

        Response response = given()
                .when()
                .get("/400")
                .then()
                .extract()
                .response();

        return response;
    }

    static private void notEmptyAddParam(Map<String, Object> payload, String key, Object value) {
        if (value != null && !value.toString().isEmpty()) {
            payload.put(key, value);
        }
    }

}
