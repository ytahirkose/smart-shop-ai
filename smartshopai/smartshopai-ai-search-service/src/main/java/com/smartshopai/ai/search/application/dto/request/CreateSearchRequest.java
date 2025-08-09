package com.smartshopai.ai.search.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSearchRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Search query is required")
    private String query;

    @NotBlank(message = "Search type is required")
    private String searchType; // SEMANTIC, KEYWORD, HYBRID, FILTERED

    @NotBlank(message = "Search context is required")
    private String context; // PRODUCT_SEARCH, RECOMMENDATION, COMPARISON

    // Search parameters
    private List<String> categories;
    private List<String> brands;
    
    @Positive(message = "Minimum price must be positive")
    private Double minPrice;
    
    @Positive(message = "Maximum price must be positive")
    private Double maxPrice;
    private String sortBy; // RELEVANCE, PRICE, RATING, POPULARITY
    private String filterBy; // AVAILABILITY, RATING, PRICE_RANGE
    private Map<String, Object> filters;

    // AI model parameters
    private String aiModel;
    private Map<String, Object> modelParameters;
    private String prompt;
}
