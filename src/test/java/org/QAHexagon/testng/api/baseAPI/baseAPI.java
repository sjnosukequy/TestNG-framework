package org.QAHexagon.testng.api.baseAPI;
import io.restassured.RestAssured;
import org.QAHexagon.testng.env.envManager;
import io.qameta.allure.restassured.AllureRestAssured;

public class baseAPI {

    static {
        // config rest assured to use BaseURI, etc....
        RestAssured.baseURI = envManager.getBaseURI();
        RestAssured.filters(new AllureRestAssured());
    }
}
