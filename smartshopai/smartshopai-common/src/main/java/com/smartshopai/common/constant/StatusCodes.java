package com.smartshopai.common.constant;

/**
 * Status codes for SmartShopAI application
 * Provides centralized status code management
 */
public final class StatusCodes {
    
    // Success status codes
    public static final String SUCCESS = "SUCCESS";
    public static final String CREATED = "CREATED";
    public static final String UPDATED = "UPDATED";
    public static final String DELETED = "DELETED";
    
    // User status codes
    public static final String USER_REGISTERED = "USER_REGISTERED";
    public static final String USER_LOGGED_IN = "USER_LOGGED_IN";
    public static final String USER_LOGGED_OUT = "USER_LOGGED_OUT";
    public static final String USER_PROFILE_UPDATED = "USER_PROFILE_UPDATED";
    
    // Product status codes
    public static final String PRODUCT_CREATED = "PRODUCT_CREATED";
    public static final String PRODUCT_UPDATED = "PRODUCT_UPDATED";
    public static final String PRODUCT_ANALYZED = "PRODUCT_ANALYZED";
    public static final String PRODUCT_COMPARED = "PRODUCT_COMPARED";
    
    // AI status codes
    public static final String AI_ANALYSIS_COMPLETED = "AI_ANALYSIS_COMPLETED";
    public static final String AI_RECOMMENDATION_GENERATED = "AI_RECOMMENDATION_GENERATED";
    public static final String AI_SEARCH_COMPLETED = "AI_SEARCH_COMPLETED";
    
    // Processing status codes
    public static final String PROCESSING = "PROCESSING";
    public static final String QUEUED = "QUEUED";
    public static final String FAILED = "FAILED";
    public static final String CANCELLED = "CANCELLED";
    
    private StatusCodes() {
        // Utility class
    }
}
