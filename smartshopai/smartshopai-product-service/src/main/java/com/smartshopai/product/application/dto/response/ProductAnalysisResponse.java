package com.smartshopai.product.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Response DTO for product analysis data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAnalysisResponse {

    private String productId;
    private LocalDateTime analyzedAt;

    // Quality assessment
    private Double qualityScore;
    private String qualityAssessment;
    private List<String> qualityPros;
    private List<String> qualityCons;

    // Value for money analysis
    private Double valueForMoneyScore;
    private String valueAssessment;
    private BigDecimal pricePerFeature;
    private String priceRecommendation;

    // Technical analysis
    private String technicalSummary;
    private List<String> technicalSpecs;
    private Map<String, String> technicalDetails;
    private String technicalRecommendation;

    // Comparison analysis
    private List<ProductComparisonResponse> comparisons;
    private String comparisonSummary;
    private String bestAlternative;

    // User recommendations
    private String userRecommendation;
    private List<String> targetAudience;
    private String useCaseRecommendation;

    // AI insights
    private String aiInsights;
    private String shoppingAdvice;
    private String alternativeSuggestions;

    // Market analysis
    private String marketPosition;
    private String competitiveAdvantage;
    private String marketTrend;

    // Price analysis
    private BigDecimal priceHistory;
    private String priceTrend;
    private String pricePrediction;
    private String discountRecommendation;

    private boolean analysisCompleted;
}
