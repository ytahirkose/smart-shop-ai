package com.smartshopai.common.exception;

import com.smartshopai.common.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for all services
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<String>> handleBaseException(BaseException ex) {
        log.error("Base exception occurred: {}", ex.getMessage(), ex);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<String>> handleBusinessException(BusinessException ex) {
        log.error("Business exception occurred: {}", ex.getMessage(), ex);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleNotFoundException(NotFoundException ex) {
        log.error("Not found exception occurred: {}", ex.getMessage(), ex);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseResponse<String>> handleValidationException(ValidationException ex) {
        log.error("Validation exception occurred: {}", ex.getMessage(), ex);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation exception occurred: {}", ex.getMessage(), ex);
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error("Validation failed", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected exception occurred: {}", ex.getMessage(), ex);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("An unexpected error occurred"));
    }
}
