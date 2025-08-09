package com.smartshopai.sessioncache.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * User session entity for tracking user sessions and behavior
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_sessions")
public class UserSession {

    @Id
    private String id;
    
    @Indexed(unique = true)
    private String sessionId;
    
    @Indexed
    private String userId;
    
    // Session information
    private String userAgent;
    private String ipAddress;
    private String deviceType; // MOBILE, DESKTOP, TABLET
    private String browser;
    private String operatingSystem;
    
    // Session state
    private String status; // ACTIVE, INACTIVE, EXPIRED, TERMINATED
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
    private String authenticationToken;
    private String refreshToken;
    private LocalDateTime tokenExpiryTime;
    private Boolean isAuthenticated;
    
    // Analytics
    private Map<String, Object> analyticsData;
    private Map<String, Double> metrics;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
    
    // Pre-update method to set updated timestamp
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper methods
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    
    public boolean isExpired() {
        return "EXPIRED".equals(status);
    }
    
    public void updateLastActivity() {
        this.lastActivityTime = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void incrementPageViews() {
        this.pageViews = (this.pageViews == null) ? 1 : this.pageViews + 1;
    }
    
    public void incrementClicks() {
        this.clicks = (this.clicks == null) ? 1 : this.clicks + 1;
    }
    
    public void incrementSearches() {
        this.searches = (this.searches == null) ? 1 : this.searches + 1;
    }
}
