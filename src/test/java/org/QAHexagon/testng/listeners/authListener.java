package org.QAHexagon.testng.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.QAHexagon.testng.annotations.Auth;
import org.QAHexagon.testng.extensions.authRetries;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

@SuppressWarnings("rawtypes")
public class authListener implements IAnnotationTransformer {
    
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // 1. Check if the method exists and has the @Auth annotation
        System.out.println("AuthListener: Detected @Auth annotation on method: " + testMethod.getName());
        if (testMethod != null && testMethod.isAnnotationPresent(Auth.class)) {
            // 2. Force the Retry Analyzer
            annotation.setRetryAnalyzer(authRetries.class);

            // Optional: You can also dynamically set groups or descriptions here
            // annotation.setGroups(new String[]{"authenticated"});
        }
    }
}
