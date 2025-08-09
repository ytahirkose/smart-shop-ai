package com.smartshopai.ai.search.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Request DTO for semantic search
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemanticSearchRequest {

    @NotBlank(message = "Search query is required")
    private String query;

    private String userId;
    private String category;
    private String brand;
    private Double minPrice;
    private Double maxPrice;
    private Integer limit;
    private Double similarityThreshold;
    private Map<String, Object> filters;
    private String context;
}
