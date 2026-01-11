package org.QAHexagon.testng.utils;
import org.QAHexagon.testng.api.authAPI.*;
import io.restassured.path.json.JsonPath;
import org.QAHexagon.testng.env.tokenManager;

public class authHelper {
    void reLogin(String email, String password) {
        // Implementation for re-login
        JsonPath response = loginAPI.login(email, password, true);
        tokenManager.setAccessToken(response.getString("result.accessToken"));
    }

    void reRefresh() {
        // Implementation for token refresh
        JsonPath response = loginAPI.refresh();
        tokenManager.setAccessToken(response.getString("result.accessToken"));
    }
}
