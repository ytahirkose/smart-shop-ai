package com.smartshopai.businessintelligence.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for tracking analytics events
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackEventRequest {

    @NotBlank(message = "Event type is required")
    private String eventType;
    
    @NotBlank(message = "Event category is required")
    private String eventCategory;
    
    @NotBlank(message = "Event action is required")
    private String eventAction;
    
    private String eventLabel;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    private String sessionId;
    private String userAgent;
    private String ipAddress;
    private String pageUrl;
    private String pageTitle;
    private String referrer;
    private String productId;
    private String categoryId;
    private String brandId;
    private Double price;
    private String currency;
    private Map<String, Object> properties;
    private Map<String, String> dimensions;
    private Map<String, Double> metrics;
    
    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;
}
