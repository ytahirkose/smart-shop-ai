package com.smartshopai.businessintelligence.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Analytics event entity for tracking user interactions and business events
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "analytics_events")
public class AnalyticsEvent {

    @Id
    private String id;
    
    // Event identification
    private String eventType; // PAGE_VIEW, CLICK, SEARCH, PURCHASE, etc.
    private String eventCategory; // USER_INTERACTION, BUSINESS_METRIC, SYSTEM_EVENT
    private String eventAction; // Specific action performed
    private String eventLabel; // Additional context
    
    // User information
    private String userId;
    private String sessionId;
    private String userAgent;
    private String ipAddress;
    
    // Page/Application context
    private String pageUrl;
    private String pageTitle;
    private String referrer;
    
    // Business context
    private String productId;
    private String categoryId;
    private String brandId;
    private Double price;
    private String currency;
    
    // Custom properties
    private Map<String, Object> properties;
    private Map<String, String> dimensions;
    private Map<String, Double> metrics;
    
    // Timestamps
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Processing status
    private String status; // PENDING, PROCESSED, FAILED
    private String processingError;
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
}
