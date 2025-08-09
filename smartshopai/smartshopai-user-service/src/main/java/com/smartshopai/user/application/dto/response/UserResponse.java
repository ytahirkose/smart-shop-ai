package com.smartshopai.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for user response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
    private boolean enabled;

    // Behavioral metrics included for other services
    private UserBehaviorMetricsResponse behaviorMetrics;

    // Timestamps
    private String createdAt;
    private String lastLoginAt;
}
