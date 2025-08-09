package com.smartshopai.ai.analysis.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Response DTO for product comparison analysis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonResponse {
    
    private String comparisonId;
    
    private List<String> productIds;
    
    private String comparisonType; // price, features, quality, overall
    
    private String comparisonContent;
    
    private String summary;
    
    private Double confidenceScore;
    
    private Map<String, Object> comparisonResults;
    
    private List<String> keyDifferences;
    
    private String recommendation;
    
    private String modelUsed;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @Builder.Default
    private boolean isActive = true;
}
