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

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recommendations")
public class Recommendation {

    @Id
    private String id;
    private String userId;
    private String requestType;
    private String context;
    
    // Product ID for specific product recommendations
    private String productId;
    
    // User preferences
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
    
    // Results
    private List<RecommendedProduct> recommendedProducts;
    private String recommendationSummary;
    private String reasoning;
    private Double confidenceScore;
    
    // Technical details
    private String aiModelUsed;
    private Integer tokensUsed;
    private Long processingTimeMs;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendedProduct {
        private String productId;
        private String productName;
        private String brand;
        private String category;
        private Double price;
        private Double score;
        private String reason;
        private List<String> features;
        private Map<String, Object> metadata;
    }
}
