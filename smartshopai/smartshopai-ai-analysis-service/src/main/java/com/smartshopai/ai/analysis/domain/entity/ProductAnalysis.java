package com.smartshopai.ai.analysis.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Product Analysis entity for AI-powered product analysis
 * Stores AI analysis results and insights
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product_analyses")
public class ProductAnalysis {
    
    @Id
    private String id;
    
    @Indexed
    private String productId;
    
    @Indexed
    private String userId;
    
    // Analysis metadata
    private String analysisType; // technical, price, quality, comparison
    
    // AI Analysis Results
    private String technicalAnalysis;
    private String priceQualityAnalysis;
    private String alternativeRecommendations;
    private String userFriendlyDescription;
    
    // AI Generated Scores
    private Double qualityScore;
    private Double valueForMoneyScore;
    private Double technicalComplexityScore;
    private Double userFriendlinessScore;
    
    // AI Generated Insights
    private List<String> keyFeatures;
    private List<String> pros;
    private List<String> cons;
    private List<String> alternatives;
    
    // AI Generated Recommendations
    private String buyRecommendation;
    private String bestTimeToBuy;
    private String targetAudience;
    
    // Technical Specifications (AI Simplified)
    private Map<String, String> simplifiedSpecs;
    private Map<String, String> technicalTerms;
    
    // AI Model Information
    private String aiModelVersion;
    private Double confidenceScore;
    private String analysisPrompt;
    
    // Metadata
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String analysisStatus; // PENDING, IN_PROGRESS, COMPLETED, FAILED
    
    // User Feedback
    private Boolean wasHelpful;
    private String userFeedback;
    
    @Builder.Default
    private boolean isPublic = false;
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
    
    // Pre-update method to set updated timestamp
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
