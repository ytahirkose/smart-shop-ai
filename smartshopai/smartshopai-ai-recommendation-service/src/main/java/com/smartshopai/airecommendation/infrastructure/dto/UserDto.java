package com.smartshopai.airecommendation.infrastructure.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserDto {
    private String id;
    private String username;
    private Set<String> roles;
    private UserProfileDto userProfile;
    private UserPreferencesDto userPreferences;
    private UserBehaviorMetricsDto userBehaviorMetrics;
}
