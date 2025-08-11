package com.smartshopai.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods as cacheable
 * Provides cache configuration options
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    
    /**
     * Cache key prefix
     */
    String keyPrefix() default "";
    
    /**
     * Cache TTL in seconds
     */
    long ttl() default 3600;
    
    /**
     * Whether to cache null values
     */
    boolean cacheNullValues() default false;
    
    /**
     * Cache condition (SpEL expression)
     */
    String condition() default "";
}
