package org.QAHexagon.testng.env;
import org.QAHexagon.testng.properties.propertiesManager;

public class tokenManager {
    private static String accessToken;
    private static String refreshToken;

    // Initialize with values from .env (if they exist manually)
    static {
        accessToken = propertiesManager.get("ACCESS_TOKEN"); 
        refreshToken = propertiesManager.get("REFRESH_TOKEN");
    }

    public static synchronized String getAccessToken() {
        return accessToken;
    }

    public static synchronized void setAccessToken(String access) {
        accessToken = access;
    }

    public static synchronized void setRefreshToken(String refresh) {
        refreshToken = refresh;
    }
    
    public static synchronized void setTokens(String access, String refresh) {
        accessToken = access;
        refreshToken = refresh;
    }

    public static synchronized String getRefreshToken() {
        return refreshToken;
    }
}