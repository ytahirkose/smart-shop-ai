package com.smartshopai.ai.recommendation.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Response DTO for AI recommendation results
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {

    private String id;
    private String userId;
    private String requestType;
    private String context;
    
    // Recommendation Results
    private List<RecommendedProduct> recommendedProducts;
    private String recommendationSummary;
    private String reasoning;
    private Double confidenceScore;
    
    // Technical Details
    private String aiModelUsed;
    private Integer tokensUsed;
    private Long processingTimeMs;
    private Map<String, Object> modelParameters;
    
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
