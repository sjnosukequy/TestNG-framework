package org.QAHexagon.testng.env;

import org.QAHexagon.testng.properties.propertiesManager;

public class envManager {
    private static String baseURI;
    static {
        baseURI = propertiesManager.get("baseURI");
    }
    public static String getBaseURI() {
        return baseURI;
    }
}
