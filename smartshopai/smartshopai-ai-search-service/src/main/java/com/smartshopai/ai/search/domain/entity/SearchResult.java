package com.smartshopai.ai.search.domain.entity;

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
 * Search Result entity for SmartShopAI application
 * Represents AI-powered search results
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "search_results")
public class SearchResult {
    
    @Id
    private String id;
    
    private String searchRequestId;
    private String userId;
    private String query;
    private String searchType;
    private String context;
    
    // Search results
    private List<SearchProduct> products;
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
    private String status; // ACTIVE, EXPIRED, ARCHIVED
    private boolean personalized;
    private boolean realTime;
    
    @Builder.Default
    private boolean aiAnalysisCompleted = false;
    
    // Nested class for search products
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchProduct {
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
