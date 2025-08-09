package com.smartshopai.businessintelligence.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Response DTO for analytics events
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsEventResponse {

    private String id;
    private String eventType;
    private String eventCategory;
    private String eventAction;
    private String eventLabel;
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
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private String processingError;
}
