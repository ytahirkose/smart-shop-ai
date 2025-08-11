package com.smartshopai.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesRequest {
    
    private String currency;
    private String language;
    private boolean emailNotifications;
    private boolean pushNotifications;
    private boolean smsNotifications;
    private String theme;
    private boolean twoFactorEnabled;
    private String defaultCategory;
    private Double budgetLimit;
    private String qualityPreference;
    
    public String getQualityPreference() {
        if (qualityPreference == null || qualityPreference.trim().isEmpty()) {
            return "MEDIUM";
        }
        return qualityPreference.toUpperCase();
    }
    
    public String getCurrency() {
        if (currency == null || currency.trim().isEmpty()) {
            return "USD";
        }
        return currency.toUpperCase();
    }
    
    public String getLanguage() {
        if (language == null || language.trim().isEmpty()) {
            return "en";
        }
        return language.toLowerCase();
    }
}
