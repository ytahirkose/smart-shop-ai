package com.smartshopai.ai.analysis.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product_comparisons")
public class ProductComparison {

    @Id
    private String id;
    
    private String userId;
    private String requestId;
    private List<String> productIds;
    private String comparisonType;
    
    // Comparison Results
    private String comparisonContent;
    private String summary;
    private String comparisonSummary;
    private String bestChoice;
    private String bestChoiceReason;
    private List<ComparisonItem> comparisonItems;
    private List<String> comparisonPoints;
    
    // AI Analysis Results
    private java.util.Map<String, Object> insights;
    private java.util.Map<String, Object> recommendations;
    private List<Double> embedding;
    
    // Technical Details
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
    public static class ComparisonItem {
        private String productId;
        private String productName;
        private Double score;
        private String pros;
        private String cons;
        private String recommendation;
    }
}
