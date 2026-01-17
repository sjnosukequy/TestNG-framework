package org.QAHexagon.testng.api.baseAPI;

import io.restassured.RestAssured;
import io.restassured.config.MatcherConfig;

import java.io.PrintStream;

import org.QAHexagon.testng.env.envManager;
import io.qameta.allure.restassured.AllureRestAssured;
import org.QAHexagon.testng.filters.errorThrow;
import org.apache.logging.log4j.io.IoBuilder;
import org.apache.logging.log4j.Level;

import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;

public class baseAPI {

    static {
        PrintStream log4jStream = IoBuilder.forLogger(RestAssured.class)
                .setLevel(Level.WARN)
                .buildPrintStream();
        // config rest assured to use BaseURI, etc....
        RestAssured.baseURI = envManager.getBaseURI();
        RestAssured.filters(new AllureRestAssured(), new RequestLoggingFilter(log4jStream), new ErrorLoggingFilter(log4jStream));
        // RestAssured.config().matcherConfig(new MatcherConfig(MatcherConfig.ErrorDescriptionType.HAMCREST));
    }
}
