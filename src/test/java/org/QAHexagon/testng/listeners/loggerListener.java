package org.QAHexagon.testng.listeners;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import org.QAHexagon.testng.utils.loggerUtils;

public class loggerListener implements IInvokedMethodListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        LOGGER.info("Starting: {}", method.getTestMethod().getMethodName());
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
