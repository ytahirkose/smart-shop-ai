package com.smartshopai.user.presentation.exception;

import com.smartshopai.common.dto.BaseResponse;
import com.smartshopai.common.dto.ErrorResponse;
import com.smartshopai.common.exception.BusinessException;
import com.smartshopai.common.exception.NotFoundException;
import com.smartshopai.common.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for User Service
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleNotFoundException(NotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of("NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.error("Resource not found", errorResponse));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleBusinessException(BusinessException ex) {
        log.warn("Business exception: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of("BUSINESS_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error("Business error occurred", errorResponse));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleValidationException(ValidationException ex) {
        log.warn("Validation exception: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of("VALIDATION_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error("Validation failed", errorResponse));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Method argument validation failed");
        
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("VALIDATION_ERROR")
                .message("Validation failed")
                .details(errors)
                .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error("Validation failed", errorResponse));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleBadCredentialsException(BadCredentialsException ex) {
        log.warn("Bad credentials: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of("AUTHENTICATION_ERROR", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(BaseResponse.error("Authentication failed", errorResponse));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.warn("Username not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of("USER_NOT_FOUND", "User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.error("User not found", errorResponse));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of("ACCESS_DENIED", "Access denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(BaseResponse.error("Access denied", errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        
        ErrorResponse errorResponse = ErrorResponse.of("INTERNAL_ERROR", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("Internal server error", errorResponse));
    }
}
