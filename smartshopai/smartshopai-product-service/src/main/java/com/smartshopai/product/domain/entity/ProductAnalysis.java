package com.smartshopai.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * AI-powered product analysis results
 * Contains detailed analysis and recommendations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAnalysis {

    private String id;
    private String productId;
    private LocalDateTime analyzedAt;
    private String analysisContent;
    private java.util.List<Double> embeddings;

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
    private List<ProductComparison> comparisons;
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

    @Builder.Default
    private boolean analysisCompleted = false;
}
