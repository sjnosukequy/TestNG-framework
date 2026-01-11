package org.QAHexagon.testng.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.QAHexagon.testng.annotations.Auth;
import org.QAHexagon.testng.annotations.Retries;
import org.QAHexagon.testng.extensions.authRetries;
import org.QAHexagon.testng.extensions.retries;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

@SuppressWarnings("rawtypes")
public class retriesListener implements IAnnotationTransformer {
    
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (testMethod != null && testMethod.isAnnotationPresent(Auth.class)) {
            // 1. Check if the method exists and has the @Auth annotation
            System.out.println("AuthListener: Detected @Auth annotation on method: " + testMethod.getName());

            // 2. Force the Retry Analyzer
            annotation.setRetryAnalyzer(authRetries.class);

            // Optional: You can also dynamically set groups or descriptions here
            // annotation.setGroups(new String[]{"authenticated"});
        }
        
        if (testMethod != null && testMethod.isAnnotationPresent(Retries.class)) {
            // 1. Check if the method exists and has the @Retries annotation
            System.out.println("AuthListener: Detected @Retries annotation on method: " + testMethod.getName());

            // 2. Force the Retry Analyzer
            annotation.setRetryAnalyzer(retries.class);
        }
    }
}
