package com.smartshopai.ai.recommendation.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * Request DTO for updating user preferences
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPreferencesRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    private List<String> preferredCategories;
    private List<String> preferredBrands;
    private Double maxBudget;
    private Map<String, Object> shoppingPreferences;
    private List<String> interests;
    private String context;
}
