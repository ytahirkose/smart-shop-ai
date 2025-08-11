package com.smartshopai.common.exception;

public class ValidationException extends BusinessException {
    
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", 400);
    }
    
    public ValidationException(String message, String errorCode) {
        super(message, errorCode, 400);
    }
}
