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
 * Search Query entity for AI-powered search functionality
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "search_queries")
public class SearchQuery {
    
    @Id
    private String id;
    
    private String userId;
    private String queryText;
    private String searchType; // semantic, keyword, hybrid
    private List<String> searchResults;
    private List<Double> relevanceScores;
    private Map<String, Object> searchFilters;
    private Map<String, Object> searchMetrics;
    private Integer resultCount;
    private Double searchTime;
    private String modelUsed;
    private LocalDateTime createdAt;
    
    @Builder.Default
    private boolean isSuccessful = true;
}
