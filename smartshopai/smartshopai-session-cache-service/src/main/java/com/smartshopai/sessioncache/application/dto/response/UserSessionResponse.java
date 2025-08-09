package com.smartshopai.sessioncache.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Response DTO for user session data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionResponse {

    private String id;
    private String sessionId;
    private String userId;
    
    // Session information
    private String userAgent;
    private String ipAddress;
    private String deviceType;
    private String browser;
    private String operatingSystem;
    
    // Session state
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime lastActivityTime;
    private LocalDateTime endTime;
    private Long durationSeconds;
    
    // User activity tracking
    private Integer pageViews;
    private Integer clicks;
    private Integer searches;
    private Set<String> visitedPages;
    private Set<String> searchedTerms;
    private Set<String> viewedProducts;
    
    // Session context
    private String currentPage;
    private String referrer;
    private Map<String, Object> sessionData;
    private Map<String, String> cookies;
    
    // AI and personalization
    private Map<String, Object> aiContext;
    private Map<String, Object> recommendations;
    private Map<String, Object> userPreferences;
    
    // Security
    private Boolean isAuthenticated;
    private LocalDateTime tokenExpiryTime;
    
    // Analytics
    private Map<String, Object> analyticsData;
    private Map<String, Double> metrics;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
