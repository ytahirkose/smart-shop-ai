package com.smartshopai.ai.recommendation.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Request DTO for getting recommendations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecommendationsRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    private String category;
    private String brand;
    private Double maxPrice;
    private Integer limit;
    private String recommendationType; // PERSONALIZED, TRENDING, SIMILAR, ALTERNATIVE
    private List<String> excludeProductIds;
    private Map<String, Object> preferences;
    private String context;
}
