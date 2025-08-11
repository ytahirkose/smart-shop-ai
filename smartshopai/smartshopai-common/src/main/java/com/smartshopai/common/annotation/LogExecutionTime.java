package com.smartshopai.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to log method execution time
 * Can be used on methods to automatically log execution time
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
    
    /**
     * Optional custom message for the log
     */
    String value() default "";
    
    /**
     * Whether to log method parameters
     */
    boolean logParameters() default false;
    
    /**
     * Whether to log return value
     */
    boolean logReturnValue() default false;
}
