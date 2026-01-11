package org.QAHexagon.testng.env;

import org.QAHexagon.testng.properties.propertiesManager;

public class envManager {
    private static String baseURI;
    private static String email;
    private static String password;

    static {
        baseURI = propertiesManager.get("baseURI");
        email = System.getenv("EMAIL");
        password = System.getenv("PASSWORD");
    }
    public static String getBaseURI() {
        return baseURI;
    }
    public static String getEmail() {
        return email;
    }
    public static String getPassword() {
        return password;
    }
}
