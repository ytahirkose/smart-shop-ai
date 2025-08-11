package com.smartshopai.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Error response DTO for API error responses
 * Provides detailed error information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    private Integer status;
    private String error;
    private String message;
    private String userMessage;
    private LocalDateTime timestamp;
    private String path;
    private String correlationId;
    private List<String> details;
    
    public static ErrorResponse of(String errorCode, String message) {
        return ErrorResponse.builder()
                .error(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    public static ErrorResponse of(String errorCode, String message, String userMessage) {
        return ErrorResponse.builder()
                .error(errorCode)
                .message(message)
                .userMessage(userMessage)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
