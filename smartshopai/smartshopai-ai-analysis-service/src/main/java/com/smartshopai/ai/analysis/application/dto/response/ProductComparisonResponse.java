package com.smartshopai.ai.analysis.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComparisonResponse {

    private String id;
    private String userId;
    private String requestId;
    private List<String> productIds;
    
    // Comparison Results
    private String comparisonSummary;
    private String bestChoice;
    private String bestChoiceReason;
    private List<ComparisonItemResponse> comparisonItems;
    private Map<String, Object> comparisonMatrix;
    
    // Analysis Details
    private String analysisPrompt;
    private String aiModelUsed;
    private Integer tokensUsed;
    private Long processingTimeMs;
    private Double confidenceScore;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComparisonItemResponse {
        private String productId;
        private String productName;
        private BigDecimal price;
        private Double score;
        private String pros;
        private String cons;
        private String recommendation;
        private Map<String, Object> features;
    }
}
