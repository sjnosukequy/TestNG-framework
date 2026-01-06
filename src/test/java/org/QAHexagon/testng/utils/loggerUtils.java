package org.QAHexagon.testng.utils;
import org.apache.logging.log4j.Logger;

public class loggerUtils {
    public static void newLine(Logger LOGGER) {
        LOGGER.info("{}", "=".repeat(70));
    }
}
