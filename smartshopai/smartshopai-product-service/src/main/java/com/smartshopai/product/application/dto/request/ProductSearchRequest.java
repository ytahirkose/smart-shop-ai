package com.smartshopai.product.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Request DTO for product search
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {

    @NotBlank(message = "Search query is required")
    private String query;

    private String userId;
    private String category;
    private String brand;
    private Double minPrice;
    private Double maxPrice;
    private String sortBy; // PRICE, RATING, POPULARITY, NEWEST
    private String sortOrder; // ASC, DESC
    private Integer page;
    private Integer size;
    private Map<String, Object> filters;
}
