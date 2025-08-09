package com.smartshopai.ai.recommendation.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecommendationRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Request type is required")
    private String requestType; // PERSONALIZED, SIMILAR_PRODUCTS, TRENDING, DEALS

    @NotBlank(message = "Context is required")
    private String context; // SEARCH, BROWSE, CART, WISHLIST

    // User preferences and context
    private List<String> preferredCategories;
    private List<String> preferredBrands;
    private Double maxBudget;
    private String shoppingPreferences;
    private Map<String, Object> userBehavior;

    // Request parameters
    private Integer limit;
    private String sortBy; // PRICE, RATING, POPULARITY, RELEVANCE
    private String filterBy; // BRAND, CATEGORY, PRICE_RANGE, RATING
    private Map<String, Object> filters;

    // AI model parameters
    private String aiModel;
    private Map<String, Object> modelParameters;
    private String prompt;
}
