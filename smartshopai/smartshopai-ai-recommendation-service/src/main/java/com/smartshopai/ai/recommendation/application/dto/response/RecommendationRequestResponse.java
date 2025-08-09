package com.smartshopai.ai.recommendation.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequestResponse {

    private String id;
    private String userId;
    private String requestType;
    private String context;
    
    // User preferences and context
    private List<String> preferredCategories;
    private List<String> preferredBrands;
    private Double maxBudget;
    private String shoppingPreferences;
    private Map<String, Object> userBehavior;
    
    // Request parameters
    private Integer limit;
    private String sortBy;
    private String filterBy;
    private Map<String, Object> filters;
    
    // AI model parameters
    private String aiModel;
    private Map<String, Object> modelParameters;
    private String prompt;
    
    // Status and tracking
    private String status;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime processedAt;
    private Long processingTimeMs;
    
    // Results
    private List<String> recommendedProductIds;
    private Map<String, Object> recommendationMetadata;
    private Double confidenceScore;
    private boolean aiAnalysisCompleted;
}
