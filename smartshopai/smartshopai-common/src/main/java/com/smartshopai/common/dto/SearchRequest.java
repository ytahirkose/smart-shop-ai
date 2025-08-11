package com.smartshopai.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    
    @Size(max = 500, message = "Search query must not exceed 500 characters")
    private String query;
    
    @Size(max = 20, message = "Categories list must not exceed 20 items")
    private List<String> categories;
    
    @Size(max = 50, message = "Brands list must not exceed 50 items")
    private List<String> brands;
    
    @Min(value = 0, message = "Minimum price must be non-negative")
    private Double minPrice;
    
    @Min(value = 0, message = "Maximum price must be non-negative")
    private Double maxPrice;
    
    @Pattern(regexp = "^(name|price|rating|relevance)$", message = "Sort by must be one of: name, price, rating, relevance")
    private String sortBy;
    
    @Pattern(regexp = "^(asc|desc)$", message = "Sort direction must be either 'asc' or 'desc'")
    private String sortDirection;
    
    private Map<String, Object> filters;
    
    @Builder.Default
    @Min(value = 0, message = "Page number must be non-negative")
    private int page = 0;
    
    @Builder.Default
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private int size = 20;
    
    public boolean hasFilters() {
        return (categories != null && !categories.isEmpty()) ||
               (brands != null && !brands.isEmpty()) ||
               minPrice != null || maxPrice != null ||
               (filters != null && !filters.isEmpty());
    }
    
    public boolean hasQuery() {
        return query != null && !query.trim().isEmpty();
    }
    
    public void validatePriceRange() {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
        }
    }
}
