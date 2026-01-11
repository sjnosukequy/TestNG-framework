package org.QAHexagon.testng.api.authAPI;

import org.QAHexagon.testng.api.baseAPI.baseAPI;
// import org.QAHexagon.testng.env.envManager;
import org.QAHexagon.testng.env.tokenManager;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

public class loginAPI extends baseAPI {

    static public JsonPath login(String email, String password, boolean setCookie) {
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

        JsonPath result = response.jsonPath();
        // tokenManager.setAccessToken(result.getString("result.accessToken"));

        return result;
    }

    static public JsonPath refresh() {
        Response response = given()
                .when()
                .cookie("refreshToken", tokenManager.getRefreshToken())
                .post("/api/v1/auth/refresh")
                .then()
                .extract()
                .response();
        // set cookies, tokens, etc... from response if needed
        JsonPath result = response.jsonPath();
        return result;
    }

    static public JsonPath test() {
        Response response = given()
                .when()
                .get("/posts/1")
                .then()
                .extract()
                .response();

        return response.jsonPath();
    }

}
