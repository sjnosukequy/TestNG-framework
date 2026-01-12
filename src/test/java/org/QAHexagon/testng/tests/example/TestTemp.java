package org.QAHexagon.testng.tests.example;

import org.QAHexagon.testng.base.baseTest;
import org.QAHexagon.testng.utils.csvHelper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Iterator;

import org.QAHexagon.testng.api.authAPI.*;
import io.restassured.path.json.JsonPath;

public class TestTemp extends baseTest {
    @Test
    public void test1() {
        JsonPath response = loginAPI.test();
        String title = response.get("title");
        System.out.println("Title: " + title);
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
