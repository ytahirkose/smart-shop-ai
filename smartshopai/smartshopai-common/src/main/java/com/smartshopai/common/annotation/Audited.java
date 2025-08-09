package com.smartshopai.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods for auditing
 * Used for tracking method calls and performance metrics
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Audited {
    
    /**
     * The name of the audit event
     */
    String value() default "";
    
    /**
     * Whether to log method parameters
     */
    boolean logParameters() default true;
    
    /**
     * Whether to log method result
     */
    boolean logResult() default false;
    
    /**
     * Whether to measure execution time
     */
    boolean measureTime() default true;
}
