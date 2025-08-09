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
public class RecommendationResultResponse {

    private String id;
    private String recommendationRequestId;
    private String userId;
    private String requestType;
    private String context;
    
    // Recommendation details
    private List<RecommendedProductResponse> recommendedProducts;
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
    private String status;
    private boolean personalized;
    private boolean realTime;
    private boolean aiAnalysisCompleted;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendedProductResponse {
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
}
