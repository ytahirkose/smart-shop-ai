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
 * Search Request entity for SmartShopAI application
 * Represents AI-powered search requests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "search_requests")
public class SearchRequest {
    
    @Id
    private String id;
    
    private String userId;
    private String query;
    private String searchType; // SEMANTIC, KEYWORD, HYBRID, FILTERED
    private String context; // PRODUCT_SEARCH, RECOMMENDATION, COMPARISON
    
    // Search parameters
    private List<String> categories;
    private List<String> brands;
    private Double minPrice;
    private Double maxPrice;
    private String sortBy; // RELEVANCE, PRICE, RATING, POPULARITY
    private String filterBy; // AVAILABILITY, RATING, PRICE_RANGE
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
    private List<String> resultProductIds;
    private Map<String, Object> searchMetadata;
    private Double relevanceScore;
    
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
