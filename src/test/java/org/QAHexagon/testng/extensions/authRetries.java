package org.QAHexagon.testng.extensions;
import org.QAHexagon.testng.env.tokenManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class authRetries implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 3; // Number of retries
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {

            // Fetch new access token logic can be added here

            LOGGER.info("Running Auth setup for role: {}", tokenManager.getAccessToken());
            retryCount++;
            return true;
        }
        return false;
    }
    
}
