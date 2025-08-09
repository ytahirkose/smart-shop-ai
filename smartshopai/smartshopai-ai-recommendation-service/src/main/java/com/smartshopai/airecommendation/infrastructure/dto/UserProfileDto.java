package com.smartshopai.airecommendation.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simplified view of UserProfile used by recommendation service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String country;
    private String city;
}
