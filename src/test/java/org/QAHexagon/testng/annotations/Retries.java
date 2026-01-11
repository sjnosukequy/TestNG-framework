package org.QAHexagon.testng.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retries {
    int maxRetries() default 3;
    long sleepMillis() default 1000;
}
