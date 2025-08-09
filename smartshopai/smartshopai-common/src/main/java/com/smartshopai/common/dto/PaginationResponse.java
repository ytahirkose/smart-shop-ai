package com.smartshopai.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Pagination response DTO for paginated API responses
 * Provides pagination metadata along with data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {
    
    private List<T> data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    
    @SuppressWarnings("unchecked")
    public static <T> PaginationResponse<T> of(List<T> data, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        return (PaginationResponse<T>) PaginationResponse.builder()
                .data((List<Object>) data)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .hasNext(page < totalPages - 1)
                .hasPrevious(page > 0)
                .build();
    }
}
