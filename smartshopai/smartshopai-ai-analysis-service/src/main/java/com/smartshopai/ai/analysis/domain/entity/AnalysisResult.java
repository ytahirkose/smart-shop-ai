package com.smartshopai.ai.analysis.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "analysis_results")
public class AnalysisResult {

    @Id
    private String id;
    
    private String analysisRequestId;
    private String userId;
    private String productId;
    private String analysisType;
    
    // AI Analysis Results
    private String analysis;
    private String summary;
    private List<String> keyPoints;
    private Map<String, Object> insights;
    private Map<String, Object> recommendations;
    
    // Technical Details
    private String aiModelUsed;
    private Integer tokensUsed;
    private Long processingTimeMs;
    private Double confidenceScore;
    
    // Embeddings for semantic search
    private List<Double> embedding;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
