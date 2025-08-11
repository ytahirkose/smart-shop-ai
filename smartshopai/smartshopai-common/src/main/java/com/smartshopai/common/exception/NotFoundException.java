package com.smartshopai.common.exception;

public class NotFoundException extends BusinessException {
    
    public NotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue),
              "RESOURCE_NOT_FOUND", 404);
    }
    
    public NotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND", 404);
    }
}
