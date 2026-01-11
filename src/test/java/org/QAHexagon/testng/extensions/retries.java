package org.QAHexagon.testng.extensions;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import org.apache.logging.log4j.Logger;
import org.QAHexagon.testng.annotations.Retries;
import org.apache.logging.log4j.LogManager;

public class retries implements IRetryAnalyzer {
    private int retryCount = 0;
    private int maxRetryCount = 3;
    private long sleepMillis = 1000;
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean retry(ITestResult result) {
        // Read @Retries annotation once
        if (retryCount == 0) {
            Retries retries = result.getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(Retries.class);

            if (retries != null) {
                maxRetryCount = retries.maxRetries();
                sleepMillis = retries.sleepMillis();
            }
        }

        if (retryCount < maxRetryCount) {
            // add sleep between retries if needed
            retryCount++;
            LOGGER.info("Retry {} / {} - sleeping {} ms", retryCount, maxRetryCount, sleepMillis);
            sleepBeforeRetry();
            return true;
        }
        return false;
    }

    private void sleepBeforeRetry() {
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // important!
            LOGGER.warn("Retry sleep interrupted", e);
        }
    }
}
