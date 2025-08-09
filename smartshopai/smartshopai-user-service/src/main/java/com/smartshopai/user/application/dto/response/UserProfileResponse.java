package com.smartshopai.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for user profile response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    
    // AI-powered preferences
    private List<String> preferredCategories;
    private Double maxBudget;
    private String preferredBrands;
    private String shoppingPreferences;
    
    // Profile completion
    private Boolean profileCompleted;
    private Integer profileCompletionPercentage;
    
    // AI personality and insights
    private String aiPersonality;
    private String aiInsights;
    private List<String> aiRecommendations;
    
    // Additional preferences
    private String preferredPaymentMethods;
    private String preferredShippingMethods;
    private Boolean receiveNotifications;
    private Boolean receiveEmailAlerts;
    private Boolean receiveSmsAlerts;
    private Boolean receivePushNotifications;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastProfileUpdate;
}
