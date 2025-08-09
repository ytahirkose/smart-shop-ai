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
 * Recommendation Result entity for SmartShopAI application
 * Represents AI recommendation results
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recommendation_results")
public class RecommendationResult {
    
    @Id
    private String id;
    
    private String recommendationRequestId;
    private String userId;
    private String requestType;
    private String context;
    
    // Recommendation details
    private List<RecommendedProduct> recommendedProducts;
    private String recommendationSummary;
    private String reasoning;
    private String aiInsights;
    
    // AI model information
    private String aiModel;
    private Map<String, Object> modelParameters;
    private Long tokensUsed;
    private Long processingTimeMs;
    private Double confidenceScore;
    
    // User behavior analysis
    private Map<String, Object> userBehaviorAnalysis;
    private String userPersonality;
    private String shoppingPattern;
    
    // Performance metrics
    private Integer totalProductsAnalyzed;
    private Integer productsFiltered;
    private Integer productsRecommended;
    private Map<String, Object> performanceMetrics;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime expiresAt;
    
    // Status
    private String status; // ACTIVE, EXPIRED, ARCHIVED
    private boolean personalized;
    private boolean realTime;
    
    @Builder.Default
    private boolean aiAnalysisCompleted = false;
    
    // Nested class for recommended products
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
        private Double rating;
        private String imageUrl;
        private String productUrl;
        private Double relevanceScore;
        private String recommendationReason;
        private Map<String, Object> metadata;
    }
    
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
