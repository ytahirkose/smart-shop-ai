package com.smartshopai.ai.analysis.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Response DTO for product analysis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResponse {
    
    private String analysisId;
    private String productId;
    private String analysisType;
    private String analysisContent;
    private String summary;
    private Double confidenceScore;
    private List<String> keyFeatures;
    private List<String> pros;
    private List<String> cons;
    private Map<String, Object> technicalSpecs;
    private Map<String, Object> priceAnalysis;
    private Map<String, Object> qualityAnalysis;
    private String modelUsed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}
