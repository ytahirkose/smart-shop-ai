package com.smartshopai.product.domain.service;

import com.smartshopai.product.domain.entity.Product;
import com.smartshopai.product.domain.entity.ProductAnalysis;
import com.smartshopai.product.domain.entity.ProductComparison;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for AI-powered product analysis
 * Handles product quality assessment and recommendations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductAnalysisService {

    /**
     * Analyze product and generate insights
     */
    public ProductAnalysis analyzeProduct(Product product) {
        log.info("Starting AI analysis for product: {}", product.getName());
        
        ProductAnalysis analysis = ProductAnalysis.builder()
                .productId(product.getId())
                .analyzedAt(LocalDateTime.now())
                .build();
        
        // Quality assessment
        analyzeQuality(product, analysis);
        
        // Value for money analysis
        analyzeValueForMoney(product, analysis);
        
        // Technical analysis
        analyzeTechnicalSpecs(product, analysis);
        
        // Generate recommendations
        generateRecommendations(product, analysis);
        
        // Market analysis
        analyzeMarketPosition(product, analysis);
        
        // Price analysis
        analyzePrice(product, analysis);
        
        analysis.setAnalysisCompleted(true);
        
        log.info("AI analysis completed for product: {}", product.getName());
        return analysis;
    }
    
    /**
     * Analyze product quality
     */
    private void analyzeQuality(Product product, ProductAnalysis analysis) {
        double qualityScore = calculateQualityScore(product);
        
        analysis.setQualityScore(qualityScore);
        analysis.setQualityAssessment(getQualityAssessment(qualityScore));
        analysis.setQualityPros(generateQualityPros(product));
        analysis.setQualityCons(generateQualityCons(product));
    }
    
    /**
     * Analyze value for money
     */
    private void analyzeValueForMoney(Product product, ProductAnalysis analysis) {
        double valueScore = calculateValueScore(product);
        
        analysis.setValueForMoneyScore(valueScore);
        analysis.setValueAssessment(getValueAssessment(valueScore));
        analysis.setPricePerFeature(calculatePricePerFeature(product));
        analysis.setPriceRecommendation(generatePriceRecommendation(product));
    }
    
    /**
     * Analyze technical specifications
     */
    private void analyzeTechnicalSpecs(Product product, ProductAnalysis analysis) {
        analysis.setTechnicalSummary(generateTechnicalSummary(product));
        analysis.setTechnicalSpecs(extractTechnicalSpecs(product));
        // Convert Map<String, Object> to Map<String, String> for technical details
        if (product.getSpecifications() != null && product.getSpecifications().getSpecifications() != null) {
            Map<String, String> technicalDetails = new HashMap<>();
            product.getSpecifications().getSpecifications().forEach((key, value) -> 
                technicalDetails.put(key, value != null ? value.toString() : ""));
            analysis.setTechnicalDetails(technicalDetails);
        }
        analysis.setTechnicalRecommendation(generateTechnicalRecommendation(product));
    }
    
    /**
     * Generate recommendations
     */
    private void generateRecommendations(Product product, ProductAnalysis analysis) {
        analysis.setUserRecommendation(generateUserRecommendation(product));
        analysis.setTargetAudience(identifyTargetAudience(product));
        analysis.setUseCaseRecommendation(generateUseCaseRecommendation(product));
        analysis.setAiInsights(generateAiInsights(product));
        analysis.setShoppingAdvice(generateShoppingAdvice(product));
        analysis.setAlternativeSuggestions(generateAlternativeSuggestions(product));
    }
    
    /**
     * Analyze market position
     */
    private void analyzeMarketPosition(Product product, ProductAnalysis analysis) {
        analysis.setMarketPosition(assessMarketPosition(product));
        analysis.setCompetitiveAdvantage(identifyCompetitiveAdvantage(product));
        analysis.setMarketTrend(analyzeMarketTrend(product));
    }
    
    /**
     * Analyze price
     */
    private void analyzePrice(Product product, ProductAnalysis analysis) {
        analysis.setPriceHistory(analyzePriceHistory(product));
        analysis.setPriceTrend(determinePriceTrend(product));
        analysis.setPricePrediction(predictPriceMovement(product));
        analysis.setDiscountRecommendation(generateDiscountRecommendation(product));
    }
    
    // Helper methods for analysis
    private double calculateQualityScore(Product product) {
        // Simple quality score calculation based on features and specifications
        double score = 7.0; // Base score
        
        if (product.getFeatures() != null && !product.getFeatures().isEmpty()) {
            score += Math.min(product.getFeatures().size() * 0.5, 2.0);
        }
        
        if (product.getAverageRating() != null) {
            score += product.getAverageRating() * 0.3;
        }
        
        if (product.getSpecifications() != null && product.getSpecifications().getSpecifications() != null && !product.getSpecifications().getSpecifications().isEmpty()) {
            score += 1.0;
        }
        
        return Math.min(score, 10.0);
    }
    
    private double calculateValueScore(Product product) {
        // Value score based on price vs features
        double score = 6.0; // Base score
        
        if (product.getFeatures() != null && product.getPrice() != null) {
            double featuresPerDollar = product.getFeatures().size() / product.getPrice().doubleValue();
            score += Math.min(featuresPerDollar * 10, 3.0);
        }
        
        if (product.getOriginalPrice() != null && product.getPrice() != null) {
            double discount = (product.getOriginalPrice().doubleValue() - product.getPrice().doubleValue()) / product.getOriginalPrice().doubleValue();
            score += discount * 2.0;
        }
        
        return Math.min(score, 10.0);
    }
    
    private String getQualityAssessment(double score) {
        if (score >= 9.0) return "Excellent quality with premium features";
        if (score >= 7.0) return "Good quality with solid features";
        if (score >= 5.0) return "Average quality with basic features";
        return "Below average quality";
    }
    
    private String getValueAssessment(double score) {
        if (score >= 8.0) return "Excellent value for money";
        if (score >= 6.0) return "Good value for money";
        if (score >= 4.0) return "Fair value for money";
        return "Poor value for money";
    }
    
    private List<String> generateQualityPros(Product product) {
        List<String> pros = new ArrayList<>();
        pros.add("Well-established brand");
        if (product.getFeatures() != null && !product.getFeatures().isEmpty()) {
            pros.add("Rich feature set");
        }
        if (product.getAverageRating() != null && product.getAverageRating() >= 4.0) {
            pros.add("Highly rated by users");
        }
        if (product.getWarranty() != null) {
            pros.add("Includes warranty");
        }
        return pros;
    }
    
    private List<String> generateQualityCons(Product product) {
        List<String> cons = new ArrayList<>();
        if (product.getPrice() != null && product.getOriginalPrice() != null) {
            if (product.getPrice().compareTo(product.getOriginalPrice()) >= 0) {
                cons.add("No current discounts");
            }
        }
        if (product.getAverageRating() != null && product.getAverageRating() < 3.5) {
            cons.add("Below average user ratings");
        }
        return cons;
    }
    
    private BigDecimal calculatePricePerFeature(Product product) {
        if (product.getFeatures() != null && !product.getFeatures().isEmpty() && product.getPrice() != null) {
            return product.getPrice().divide(BigDecimal.valueOf(product.getFeatures().size()), 2, java.math.RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
    
    private String generatePriceRecommendation(Product product) {
        if (product.getPrice() != null && product.getOriginalPrice() != null) {
            double discount = (product.getOriginalPrice().doubleValue() - product.getPrice().doubleValue()) / product.getOriginalPrice().doubleValue();
            if (discount > 0.2) {
                return "Great deal! Significant discount available";
            } else if (discount > 0.1) {
                return "Good discount available";
            }
        }
        return "Consider waiting for better deals";
    }
    
    private String generateTechnicalSummary(Product product) {
        StringBuilder summary = new StringBuilder();
        summary.append("Technical overview of ").append(product.getName()).append(": ");
        
        if (product.getSpecifications() != null && product.getSpecifications().getSpecifications() != null && !product.getSpecifications().getSpecifications().isEmpty()) {
            summary.append("Comprehensive specifications available. ");
        }
        
        if (product.getFeatures() != null && !product.getFeatures().isEmpty()) {
            summary.append("Features ").append(product.getFeatures().size()).append(" key features. ");
        }
        
        summary.append("Suitable for users looking for ").append(product.getCategory()).append(" products.");
        
        return summary.toString();
    }
    
    private List<String> extractTechnicalSpecs(Product product) {
        List<String> specs = new ArrayList<>();
        if (product.getSpecifications() != null && product.getSpecifications().getSpecifications() != null) {
            product.getSpecifications().getSpecifications().forEach((key, value) ->
                specs.add(key + ": " + value));
        }
        return specs;
    }
    
    private String generateTechnicalRecommendation(Product product) {
        return "This product offers solid technical specifications for its category. " +
               "Consider your specific needs when evaluating the feature set.";
    }
    
    private String generateUserRecommendation(Product product) {
        return "Based on our analysis, this product is recommended for users who value " +
               "quality and are willing to invest in a reliable " + product.getCategory() + " solution.";
    }
    
    private List<String> identifyTargetAudience(Product product) {
        List<String> audience = new ArrayList<>();
        audience.add("Quality-conscious consumers");
        audience.add(product.getCategory() + " enthusiasts");
        if (product.getPrice() != null && product.getPrice().compareTo(BigDecimal.valueOf(100)) > 0) {
            audience.add("Premium buyers");
        }
        return audience;
    }
    
    private String generateUseCaseRecommendation(Product product) {
        return "Ideal for " + product.getCategory() + " applications where quality and reliability are priorities.";
    }
    
    private String generateAiInsights(Product product) {
        return "AI analysis suggests this product offers good value for its category. " +
               "Consider comparing with alternatives for the best deal.";
    }
    
    private String generateShoppingAdvice(Product product) {
        return "Research alternatives in the same price range and read user reviews " +
               "before making a final decision.";
    }
    
    private String generateAlternativeSuggestions(Product product) {
        return "Consider exploring similar products from competing brands " +
               "to ensure you're getting the best value for your money.";
    }
    
    private String assessMarketPosition(Product product) {
        return "This product holds a competitive position in the " + product.getCategory() + " market.";
    }
    
    private String identifyCompetitiveAdvantage(Product product) {
        if (product.getFeatures() != null && !product.getFeatures().isEmpty()) {
            int featureCount = product.getFeatures().size();
            if (featureCount > 10) {
                return "Rich feature set outperforms many competitors (" + featureCount + " listed features)";
            }
        }
        if (product.getAverageRating() != null && product.getAverageRating() >= 4.5) {
            return "Exceptional user satisfaction with an average rating of " + product.getAverageRating();
        }
        if (Boolean.TRUE.equals(product.getFeatured())) {
            return "Featured product in its category indicating strong vendor backing.";
        }
        return "Brand recognition and balanced feature-price ratio provide competitive edge.";
    }

    private String analyzeMarketTrend(Product product) {
        if (product.getViewCount() > 2000) {
            return "High and increasing view counts indicate rising market interest.";
        }
        if (product.getAverageRating() != null && product.getAverageRating() >= 4.5) {
            return "Positive user sentiment suggests steady demand in the market.";
        }
        return "Market demand appears steady for this category.";
    }

    private java.math.BigDecimal analyzePriceHistory(Product product) {
        if (product.getPriceHistories() != null && !product.getPriceHistories().isEmpty()) {
            // Return the most recent price in history
            return product.getPriceHistories().get(0).getPrice();
        }
        return product.getPrice();
    }

    private String determinePriceTrend(Product product) {
        if (product.getPriceHistories() != null && product.getPriceHistories().size() >= 2) {
            var latest = product.getPriceHistories().get(0).getPrice();
            var previous = product.getPriceHistories().get(1).getPrice();
            int cmp = latest.compareTo(previous);
            if (cmp < 0) return "Downward price trend";
            if (cmp > 0) return "Upward price trend";
        }
        return "Stable";
    }

    private String predictPriceMovement(Product product) {
        if (product.getOriginalPrice() != null && product.getPrice() != null) {
            double discount = (product.getOriginalPrice().doubleValue() - product.getPrice().doubleValue()) / product.getOriginalPrice().doubleValue();
            if (discount > 0.25) {
                return "Price already discounted significantly; further drops unlikely soon.";
            }
        }
        if (product.getPriceHistories() != null && product.getPriceHistories().size() >= 3) {
            // Simple trend: compare last price with avg of previous
            java.util.List<java.math.BigDecimal> prices = product.getPriceHistories().stream()
                    .map(ph -> ph.getPrice())
                    .toList();
            java.math.BigDecimal latest = prices.get(0);
            java.math.BigDecimal avgPrev = prices.stream().skip(1).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
                    .divide(java.math.BigDecimal.valueOf(prices.size() - 1), 2, java.math.RoundingMode.HALF_UP);
            if (latest.compareTo(avgPrev) < 0) {
                return "Recent downward trend suggests potential price increase back to average soon.";
            } else if (latest.compareTo(avgPrev) > 0) {
                return "Prices show an upward trend; waiting might not yield savings.";
            }
        }
        return "Prices are expected to remain stable over the next quarter.";
    }

    private String generateDiscountRecommendation(Product product) {
        if (product.getPrice() != null && product.getOriginalPrice() != null) {
            double discount = (product.getOriginalPrice().doubleValue() - product.getPrice().doubleValue()) / product.getOriginalPrice().doubleValue();
            if (discount >= 0.3) {
                return "Take advantage of the current \u226530% discount—great buy right now!";
            }
            if (discount >= 0.15) {
                return "Decent discount available. Consider purchasing if it fits your budget.";
            }
        }
        String season = java.time.Month.from(java.time.LocalDate.now()).toString();
        if (season.equals("NOVEMBER") || season.equals("DECEMBER")) {
            return "Wait for Black Friday/Cyber Monday deals for potential significant savings.";
        }
        return "Set up a price alert and monitor for promotional events in the coming weeks.";
    }
}

