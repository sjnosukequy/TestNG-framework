package org.QAHexagon.testng.utils;
import org.QAHexagon.testng.api.authAPI.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.QAHexagon.testng.env.tokenManager;

public class authHelper {
    void reLogin(String email, String password) {
        // Implementation for re-login
        Response response = loginAPI.login(email, password, true);
        JsonPath jsonPath = response.jsonPath();
        tokenManager.setAccessToken(jsonPath.getString("result.accessToken"));
    }

    void reRefresh() {
        // Implementation for token refresh
        Response response = loginAPI.refresh();
        JsonPath jsonPath = response.jsonPath();
        tokenManager.setAccessToken(jsonPath.getString("result.accessToken"));
    }
}
