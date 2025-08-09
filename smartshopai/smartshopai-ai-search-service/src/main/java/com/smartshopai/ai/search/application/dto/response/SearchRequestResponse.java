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
public class SearchRequestResponse {

    private String id;
    private String userId;
    private String query;
    private String searchType;
    private String context;
    
    // Search parameters
    private List<String> categories;
    private List<String> brands;
    private Double minPrice;
    private Double maxPrice;
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
    private List<String> resultProductIds;
    private Map<String, Object> searchMetadata;
    private Double relevanceScore;
    private boolean aiAnalysisCompleted;
}
