package org.QAHexagon.testng.tests.example;
import org.QAHexagon.testng.base.baseTest;
import org.testng.annotations.Test;
import org.QAHexagon.testng.api.authAPI.*;
import io.restassured.path.json.JsonPath;

public class TestTemp extends baseTest {
    @Test
    public void test1() {
        JsonPath response = loginAPI.test();
        String title = response.get("title");
        System.out.println("Title: " + title);
    }
}
