package com.smartshopai.ai.recommendation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * User Preference entity for SmartShopAI application
 * Represents user preferences for AI recommendations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_preferences")
public class UserPreference {
    
    @Id
    private String id;
    
    private String userId;
    
    // Basic preferences
    private List<String> preferredCategories;
    private List<String> preferredBrands;
    private List<String> excludedCategories;
    private List<String> excludedBrands;
    private Double minBudget;
    private Double maxBudget;
    private String preferredPriceRange;
    
    // Shopping behavior preferences
    private String shoppingFrequency;
    private String preferredShoppingTime;
    private String preferredPaymentMethod;
    private String preferredShippingMethod;
    private boolean freeShippingPreferred;
    
    // AI recommendation preferences
    private boolean personalizedRecommendations;
    private String recommendationFrequency;
    private boolean realTimeRecommendations;
    private boolean dealAlerts;
    private boolean priceDropAlerts;
    private boolean newProductAlerts;
    
    // Privacy preferences
    private boolean dataSharingEnabled;
    private boolean personalizedAdsEnabled;
    private boolean thirdPartyDataSharing;
    
    // AI-generated insights
    private String aiPersonality;
    private String shoppingPattern;
    private String recommendationPreferences;
    private Map<String, Object> aiInsights;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAnalysisAt;
    
    // Status
    private boolean active;
    private boolean preferencesCompleted;
    
    @Builder.Default
    private boolean aiAnalysisCompleted = false;
    
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
}
