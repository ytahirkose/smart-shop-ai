package com.smartshopai.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods for caching
 * Used for method result caching with configurable TTL
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    
    /**
     * The cache name
     */
    String value();
    
    /**
     * Cache key expression (SpEL)
     */
    String key() default "";
    
    /**
     * Time to live in seconds
     */
    long ttl() default 300; // 5 minutes default
    
    /**
     * Whether to cache null results
     */
    boolean cacheNull() default false;
}
