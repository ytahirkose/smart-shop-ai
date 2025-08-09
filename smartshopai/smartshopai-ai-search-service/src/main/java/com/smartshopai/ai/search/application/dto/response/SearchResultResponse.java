package com.smartshopai.ai.search.application.dto.response;

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
public class SearchResultResponse {

    private String id;
    private String searchRequestId;
    private String userId;
    private String query;
    private String searchType;
    private String context;
    
    // Search results
    private List<SearchProductResponse> products;
    private String searchSummary;
    private String reasoning;
    private String aiInsights;
    
    // AI model information
    private String aiModel;
    private Map<String, Object> modelParameters;
    private Long tokensUsed;
    private Long processingTimeMs;
    private Double confidenceScore;
    
    // Search performance metrics
    private Integer totalProductsAnalyzed;
    private Integer productsFiltered;
    private Integer productsReturned;
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
    public static class SearchProductResponse {
        private String productId;
        private String productName;
        private String brand;
        private String category;
        private Double price;
        private Double rating;
        private String imageUrl;
        private String productUrl;
        private Double relevanceScore;
        private String matchReason;
        private Map<String, Object> metadata;
    }
}
