package com.smartshopai.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * DTO for user profile update request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phoneNumber;
    
    // AI-powered preferences
    private List<String> preferredCategories;
    
    @Positive(message = "Max budget must be positive")
    private Double maxBudget;
    
    private String preferredBrands;
    private String shoppingPreferences;
    
    // Profile completion
    private Boolean profileCompleted;
    
    // AI personality
    private String aiPersonality;
    
    // Additional preferences
    private String preferredPaymentMethods;
    private String preferredShippingMethods;
    private Boolean receiveNotifications;
    private Boolean receiveEmailAlerts;
    private Boolean receiveSmsAlerts;
    private Boolean receivePushNotifications;
}
