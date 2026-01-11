package org.QAHexagon.testng.tests.example;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import org.QAHexagon.testng.base.baseTest;
import org.testng.annotations.Test;

import org.QAHexagon.testng.annotations.Auth;
import org.QAHexagon.testng.annotations.Retries;

import static org.testng.Assert.*;

public class StepTest extends baseTest {

    private static final String GLOBAL_PARAMETER = "global value";

    @Test
    public void annotatedStepTest() {
        annotatedStep("local value");
    }
    

    @Auth
    @Test
    public void testRetries() {
        assertEquals(1 + 1, 3);
    }

    @Auth(maxRetries = 5)
    @Test
    public void testRetries2() {
        assertEquals(1 + 1, 3);
    }

    @Test
    @Retries(maxRetries = 5)
    public void lambdaStepTest() {
        final String localParameter = "parameter value";
        Allure.step(String.format("Parent lambda step with parameter [%s]", localParameter), (step) -> {
            step.parameter("parameter", localParameter);
            Allure.step(String.format("Nested lambda step with global parameter [%s]", GLOBAL_PARAMETER));
        });
    }

    @Step("Parent annotated step with parameter [{parameter}]")
    public void annotatedStep(final String parameter) {
        nestedAnnotatedStep();
    }

    @Step("Nested annotated step with global parameter [{this.GLOBAL_PARAMETER}]")
    public void nestedAnnotatedStep() {

    }

}
