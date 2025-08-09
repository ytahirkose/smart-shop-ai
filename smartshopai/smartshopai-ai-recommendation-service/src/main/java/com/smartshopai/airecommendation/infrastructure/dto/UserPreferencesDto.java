package com.smartshopai.airecommendation.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Preferences subset transferred to recommendation service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesDto {
    private List<String> preferredCategories;
    private Double budgetLimit; // e.g., max price user comfortable with
    private String preferredBrands;
}
