package com.smartshopai.search.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Response DTO for search operations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    private String query;
    private List<SearchResult> results;
    private Integer totalResults;
    private Integer page;
    private Integer size;
    private Map<String, Object> facets;
    private Map<String, Object> metadata;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchResult {
        private String id;
        private String name;
        private String description;
        private String brand;
        private String category;
        private Double price;
        private Double rating;
        private String imageUrl;
        private Map<String, Object> attributes;
        private Double relevanceScore;
    }
}
