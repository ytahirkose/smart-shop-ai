package com.smartshopai.common.constant;

/**
 * Error codes for SmartShopAI application
 * Provides centralized error code management
 */
public final class ErrorCodes {
    
    // User related errors
    public static final String USER_NOT_FOUND = "USER_001";
    public static final String USER_ALREADY_EXISTS = "USER_002";
    public static final String INVALID_CREDENTIALS = "USER_003";
    public static final String USER_ACCOUNT_LOCKED = "USER_004";
    
    // Product related errors
    public static final String PRODUCT_NOT_FOUND = "PRODUCT_001";
    public static final String PRODUCT_ALREADY_EXISTS = "PRODUCT_002";
    public static final String PRODUCT_ANALYSIS_FAILED = "PRODUCT_003";
    
    // AI related errors
    public static final String AI_ANALYSIS_FAILED = "AI_001";
    public static final String AI_RECOMMENDATION_FAILED = "AI_002";
    public static final String AI_SEARCH_FAILED = "AI_003";
    public static final String OPENAI_API_ERROR = "AI_004";
    
    // Validation errors
    public static final String VALIDATION_ERROR = "VALIDATION_001";
    public static final String INVALID_INPUT = "VALIDATION_002";
    
    // Authentication errors
    public static final String UNAUTHORIZED = "AUTH_001";
    public static final String FORBIDDEN = "AUTH_002";
    public static final String INVALID_TOKEN = "AUTH_003";
    public static final String TOKEN_EXPIRED = "AUTH_004";
    
    // System errors
    public static final String INTERNAL_SERVER_ERROR = "SYSTEM_001";
    public static final String SERVICE_UNAVAILABLE = "SYSTEM_002";
    public static final String DATABASE_ERROR = "SYSTEM_003";
    public static final String EXTERNAL_SERVICE_ERROR = "SYSTEM_004";
    
    private ErrorCodes() {
        // Utility class
    }
}
