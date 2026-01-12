package org.QAHexagon.testng.listeners;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.util.Map;

import org.QAHexagon.testng.utils.loggerUtils;

public class loggerListener implements IInvokedMethodListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings("unchecked")
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        LOGGER.info("Starting: {}", method.getTestMethod().getMethodName());
        Object[] parameters = testResult.getParameters();
        if (parameters.length > 0) {
            for (Object param : parameters) {
                if (param instanceof Map) {
                    // Special handling for Map parameters
                    Map<String, String> row = (Map<String, String>) param;
                    row.forEach((key, value) -> LOGGER.info("Param (key = value): {} = {}", key, value));
                } else {
                    LOGGER.info("Param: {}", param.toString());
                }
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        String testName = testResult.getInstanceName() + "." + testResult.getName();
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LOGGER.error("Test failed: {}", testName);
            LOGGER.error("Cause: ", testResult.getThrowable());
            Allure.addAttachment("Failure Reason", testResult.getThrowable().getMessage());
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            LOGGER.warn("Test skipped: {}", testName);
        } else {
            LOGGER.info("Test passed: {}", testName);
        }
        loggerUtils.newLine(LOGGER);
    }
}
