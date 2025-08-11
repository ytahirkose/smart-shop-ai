package com.smartshopai.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse<T> {
    
    private List<T> results;
    private long totalResults;
    private int page;
    private int size;
    private int totalPages;
    private Map<String, Object> facets;
    private String query;
    private long executionTimeMs;
    
    public static <T> SearchResponse<T> of(List<T> results, long totalResults, int page, int size) {
        int totalPages = (int) Math.ceil((double) totalResults / size);
        
        return SearchResponse.<T>builder()
                .results(results)
                .totalResults(totalResults)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .build();
    }
}
