package com.smartshopai.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods for validation
 * Used for automatic parameter validation
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validated {
    
    /**
     * Validation groups to apply
     */
    Class<?>[] groups() default {};
    
    /**
     * Whether to validate all parameters
     */
    boolean validateAll() default true;
    
    /**
     * Whether to throw exception on validation failure
     */
    boolean throwOnFailure() default true;
}
