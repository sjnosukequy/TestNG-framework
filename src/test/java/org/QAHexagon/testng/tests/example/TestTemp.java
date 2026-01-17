package org.QAHexagon.testng.tests.example;

import org.QAHexagon.testng.base.baseTest;
import org.QAHexagon.testng.utils.csvHelper;
import org.hamcrest.Matcher;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

// import static org.testng.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Iterator;

import org.QAHexagon.testng.api.authAPI.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.lessThan;

public class TestTemp extends baseTest {
    @Test
    public void test1() {
        Response response = loginAPI.test();
        JsonPath jsonPath = response.jsonPath();
        String title = jsonPath.get("title");
        System.out.println("Title: " + title);
    }

    @Test
    public void test2() {
        // JsonPath response = loginAPI.param(1, null);
        Response response = loginAPI.error();
        // System.out.println("Response: " + response.prettyPrint());
    }

    @Test
    public void test3() {
        Response response = loginAPI.param(1, 2);
        JsonPath jsonPath = response.jsonPath();
        int statusCode = response.getStatusCode();
        assertThat("Unexpected HTTP error", statusCode, lessThan(400));
        // JsonPath response = loginAPI.error();
        // System.out.println("Response: " + response.prettyPrint());
    }

    @DataProvider(name = "test1")
    public Iterator<Object[]> createData1() {
        return csvHelper.readCSV("testdata/names.csv");
    }

    @Test(dataProvider = "test1")
    public void verifyData1(Map<String, String> row) {
        row.forEach((key, value) -> System.out.println(key + ": " + value));
        // System.out.println(
        //         row.get("Last Name") + " " + row.get("First Name"));
    }

}
