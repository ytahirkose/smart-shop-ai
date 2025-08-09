package com.smartshopai.ai.recommendation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Recommendation Request entity for SmartShopAI application
 * Represents AI recommendation requests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recommendation_requests")
public class RecommendationRequest {
    
    @Id
    private String id;
    
    private String userId;
    private String requestType; // PERSONALIZED, SIMILAR_PRODUCTS, TRENDING, DEALS
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
    
    // Status and tracking
    private String status; // PENDING, PROCESSING, COMPLETED, FAILED
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime processedAt;
    private Long processingTimeMs;
    
    // Results
    private List<String> recommendedProductIds;
    private Map<String, Object> recommendationMetadata;
    private Double confidenceScore;
    
    @Builder.Default
    private boolean aiAnalysisCompleted = false;
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
    
    // Pre-update method to set updated timestamp
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
