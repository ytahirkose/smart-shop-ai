package com.smartshopai.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods for auditing
 * Automatically logs method calls and results
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Audited {
    
    /**
     * Audit event type
     */
    String eventType() default "";
    
    /**
     * Whether to log method parameters
     */
    boolean logParameters() default true;
    
    /**
     * Whether to log return value
     */
    boolean logReturnValue() default false;
    
    /**
     * Whether to log execution time
     */
    boolean logExecutionTime() default true;
}
