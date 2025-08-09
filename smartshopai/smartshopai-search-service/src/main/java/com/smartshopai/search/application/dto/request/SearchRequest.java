package com.smartshopai.search.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Request DTO for search operations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    @NotBlank(message = "Search query is required")
    private String query;

    private String userId;
    private String category;
    private String brand;
    private Double minPrice;
    private Double maxPrice;
    private String sortBy; // RELEVANCE, PRICE, RATING, POPULARITY
    private String sortOrder; // ASC, DESC
    private Integer page;
    private Integer size;
    private Map<String, Object> filters;
}
