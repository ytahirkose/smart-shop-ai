package com.smartshopai.common.constant;

public final class ErrorCodes {
    
    private ErrorCodes() {}
    
    // General Errors
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
    public static final String ACCESS_DENIED = "ACCESS_DENIED";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    
    // User Errors
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String ACCOUNT_LOCKED = "ACCOUNT_LOCKED";
    public static final String ACCOUNT_DISABLED = "ACCOUNT_DISABLED";
    
    // Product Errors
    public static final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";
    public static final String PRODUCT_ALREADY_EXISTS = "PRODUCT_ALREADY_EXISTS";
    public static final String INVALID_PRODUCT_DATA = "INVALID_PRODUCT_DATA";
    
    // AI Analysis Errors
    public static final String AI_ANALYSIS_FAILED = "AI_ANALYSIS_FAILED";
    public static final String AI_SERVICE_UNAVAILABLE = "AI_SERVICE_UNAVAILABLE";
    public static final String INVALID_AI_PROMPT = "INVALID_AI_PROMPT";
    
    // Database Errors
    public static final String DATABASE_CONNECTION_ERROR = "DATABASE_CONNECTION_ERROR";
    public static final String DATABASE_QUERY_ERROR = "DATABASE_QUERY_ERROR";
    public static final String DATABASE_TRANSACTION_ERROR = "DATABASE_TRANSACTION_ERROR";
    
    // External Service Errors
    public static final String EXTERNAL_SERVICE_ERROR = "EXTERNAL_SERVICE_ERROR";
    public static final String EXTERNAL_SERVICE_TIMEOUT = "EXTERNAL_SERVICE_TIMEOUT";
    public static final String EXTERNAL_SERVICE_UNAVAILABLE = "EXTERNAL_SERVICE_UNAVAILABLE";
}
