package com.smartshopai.ai.recommendation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * User Recommendation entity for AI-powered personalized recommendations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_recommendations")
public class UserRecommendation {
    
    @Id
    private String id;
    
    private String userId;
    private String recommendationType; // collaborative, content-based, hybrid
    private List<String> recommendedProductIds;
    private List<Double> similarityScores;
    private Map<String, Object> userPreferences;
    private Map<String, Object> recommendationFactors;
    private Double confidenceScore;
    private String modelUsed;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    
    @Builder.Default
    private boolean isActive = true;
    
    @Builder.Default
    private boolean isViewed = false;
}
