package com.smartshopai.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {
    
    @Builder.Default
    private int page = 0;
    
    @Builder.Default
    private int size = 20;
    
    private String sortBy;
    
    @Builder.Default
    private String sortDirection = "ASC";
    
    public int getPage() {
        return Math.max(0, page);
    }
    
    public int getSize() {
        return Math.min(Math.max(1, size), 100);
    }
    
    public String getSortDirection() {
        if (sortDirection == null || sortDirection.trim().isEmpty()) {
            return "ASC";
        }
        return sortDirection.toUpperCase();
    }
}
