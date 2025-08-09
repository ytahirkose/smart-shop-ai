package com.smartshopai.ai.recommendation.domain.service;

import com.smartshopai.ai.recommendation.domain.entity.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * AI Recommendation Engine Service
 * Handles AI-powered recommendation generation
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AIRecommendationEngineService {

    private final ChatClient.Builder chatClientBuilder;

    public Recommendation generatePersonalizedRecommendations(Recommendation recommendation) {
        log.info("Generating personalized recommendations for user: {}", recommendation.getUserId());
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Create personalized recommendation prompt
            String prompt = createPersonalizedPrompt(recommendation);
            
            // Generate AI recommendations using Spring AI
            String aiResponse = chatClientBuilder.build().prompt().user(prompt).call().content();
            
            // Parse AI response and update recommendation
            recommendation.setRecommendationSummary(extractSummary(aiResponse));
            recommendation.setReasoning(extractReasoning(aiResponse));
            recommendation.setConfidenceScore(calculateConfidence(aiResponse));
            recommendation.setAiModelUsed("spring-ai-openai");
            recommendation.setTokensUsed(estimateTokens(aiResponse));
            recommendation.setProcessingTimeMs(System.currentTimeMillis() - startTime);
            
            log.info("Personalized recommendations generated successfully for user: {}", recommendation.getUserId());
            return recommendation;
            
        } catch (Exception e) {
            log.error("Error generating personalized recommendations for user: {}", recommendation.getUserId(), e);
            throw new com.smartshopai.common.exception.BusinessException("Recommendation generation failed", e);
        }
    }

    public Recommendation generateSimilarProductsRecommendation(String productId, String userId) {
        log.info("Generating similar products recommendation for product: {}", productId);
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Create similar products prompt
            String prompt = createSimilarProductsPrompt(productId);
            
            // Generate AI recommendations using Spring AI
            String aiResponse = chatClientBuilder.build().prompt().user(prompt).call().content();
            
            // Create recommendation object
            Recommendation recommendation = Recommendation.builder()
                    .userId(userId)
                    .requestType("SIMILAR_PRODUCTS")
                    .context("PRODUCT_SEARCH")
                    .productId(productId)
                    .recommendationSummary(extractSummary(aiResponse))
                    .reasoning(extractReasoning(aiResponse))
                    .confidenceScore(calculateConfidence(aiResponse))
                    .aiModelUsed("spring-ai-openai")
                    .tokensUsed(estimateTokens(aiResponse))
                    .processingTimeMs(System.currentTimeMillis() - startTime)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            log.info("Similar products recommendation generated successfully for product: {}", productId);
            return recommendation;
            
        } catch (Exception e) {
            log.error("Error generating similar products recommendation for product: {}", productId, e);
            throw new com.smartshopai.common.exception.BusinessException("Similar products recommendation failed", e);
        }
    }

    public Recommendation generateTrendingRecommendations() {
        log.info("Generating trending products recommendations");
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Create trending products prompt
            String prompt = createTrendingPrompt();
            
            // Generate AI recommendations using Spring AI
            String aiResponse = chatClientBuilder.build().prompt().user(prompt).call().content();
            
            // Create recommendation object
            Recommendation recommendation = Recommendation.builder()
                    .userId("system")
                    .requestType("TRENDING_PRODUCTS")
                    .context("DISCOVERY")
                    .recommendationSummary(extractSummary(aiResponse))
                    .reasoning(extractReasoning(aiResponse))
                    .confidenceScore(calculateConfidence(aiResponse))
                    .aiModelUsed("spring-ai-openai")
                    .tokensUsed(estimateTokens(aiResponse))
                    .processingTimeMs(System.currentTimeMillis() - startTime)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            log.info("Trending products recommendations generated successfully");
            return recommendation;
            
        } catch (Exception e) {
            log.error("Error generating trending products recommendations", e);
            throw new com.smartshopai.common.exception.BusinessException("Trending recommendations failed", e);
        }
    }

    public Recommendation generateDealRecommendations() {
        log.info("Generating deal recommendations");
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Create deal recommendations prompt
            String prompt = createDealPrompt();
            
            // Generate AI recommendations using Spring AI
            String aiResponse = chatClientBuilder.build().prompt().user(prompt).call().content();
            
            // Create recommendation object
            Recommendation recommendation = Recommendation.builder()
                    .userId("system")
                    .requestType("DEAL_RECOMMENDATIONS")
                    .context("DEALS")
                    .recommendationSummary(extractSummary(aiResponse))
                    .reasoning(extractReasoning(aiResponse))
                    .confidenceScore(calculateConfidence(aiResponse))
                    .aiModelUsed("spring-ai-openai")
                    .tokensUsed(estimateTokens(aiResponse))
                    .processingTimeMs(System.currentTimeMillis() - startTime)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            log.info("Deal recommendations generated successfully");
            return recommendation;
            
        } catch (Exception e) {
            log.error("Error generating deal recommendations", e);
            throw new com.smartshopai.common.exception.BusinessException("Deal recommendations failed", e);
        }
    }

    private String createPersonalizedPrompt(Recommendation recommendation) {
        return String.format("""
            Generate personalized product recommendations for a user with the following preferences:
            
            User ID: %s
            Preferred Categories: %s
            Preferred Brands: %s
            Max Budget: $%.2f
            Shopping Preferences: %s
            
            Context: %s
            Request Type: %s
            
            Please provide:
            1. A summary of recommended products
            2. Reasoning for each recommendation
            3. Confidence score for the recommendations
            4. Alternative suggestions
            
            Format the response in a clear, structured manner.
            """, 
            recommendation.getUserId(),
            recommendation.getPreferredCategories() != null ? String.join(", ", recommendation.getPreferredCategories()) : "Not specified",
            recommendation.getPreferredBrands() != null ? String.join(", ", recommendation.getPreferredBrands()) : "Not specified",
            recommendation.getMaxBudget() != null ? recommendation.getMaxBudget() : 0.0,
            recommendation.getShoppingPreferences() != null ? recommendation.getShoppingPreferences() : "Not specified",
            recommendation.getContext(),
            recommendation.getRequestType()
        );
    }

    private String createSimilarProductsPrompt(String productId) {
        return String.format("""
            Find similar products to product ID: %s
            
            Please provide:
            1. A list of similar products with reasoning
            2. Key similarities and differences
            3. Recommendations based on product features
            4. Alternative options
            
            Format the response in a clear, structured manner.
            """, productId);
    }

    private String createTrendingPrompt() {
        return """
            Generate trending product recommendations based on current market analysis.
            
            Please provide:
            1. Currently trending products
            2. Reasons for their popularity
            3. Market trends analysis
            4. Recommendations for different user segments
            
            Format the response in a clear, structured manner.
            """;
    }

    private String createDealPrompt() {
        return """
            Generate deal recommendations based on current offers and discounts.
            
            Please provide:
            1. Best deals currently available
            2. Value for money analysis
            3. Limited-time offers
            4. Recommendations for different budgets
            
            Format the response in a clear, structured manner.
            """;
    }

    private String extractSummary(String aiResponse) {
        // Simple extraction - in production, use more sophisticated parsing
        String[] sentences = aiResponse.split("\\.");
        if (sentences.length > 0) {
            return sentences[0].trim() + ".";
        }
        return aiResponse.substring(0, Math.min(200, aiResponse.length())) + "...";
    }

    private String extractReasoning(String aiResponse) {
        // Attempt to capture a dedicated reasoning section if present
        if (aiResponse == null || aiResponse.isBlank()) {
            return "No reasoning available";
        }
        String lower = aiResponse.toLowerCase();
        int idx = -1;
        // Try common markers
        for (String marker : List.of("reasoning:", "reason:", "why we recommend", "analysis:")) {
            int pos = lower.indexOf(marker);
            if (pos != -1) {
                idx = pos + marker.length();
                break;
            }
        }
        if (idx != -1) {
            // Extract until next line break or max 300 chars
            int end = aiResponse.indexOf('\n', idx);
            if (end == -1 || end - idx > 300) {
                end = Math.min(idx + 300, aiResponse.length());
            }
            return aiResponse.substring(idx, end).trim();
        }
        // Fallback: return first 250 chars as reasoning snippet
        return aiResponse.substring(0, Math.min(250, aiResponse.length())).trim() + (aiResponse.length() > 250 ? "..." : "");
    }

    private Double calculateConfidence(String aiResponse) {
        if (aiResponse == null || aiResponse.isBlank()) {
            return 0.0;
        }
        // Look for an explicit confidence score in the response (e.g., "Confidence: 0.87")
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(?i)confidence[\n\r:\\s]*([0-9]+(?:\\.[0-9]+)?)").matcher(aiResponse);
        if (matcher.find()) {
            try {
                double parsed = Double.parseDouble(matcher.group(1));
                if (parsed <= 1.0) {
                    return parsed; // Already between 0 and 1
                }
                // If percentage value like 85, convert to 0-1 range
                return parsed / 100.0;
            } catch (NumberFormatException ignored) {}
        }
        // Heuristic: base score on number of recommended items (more items -> lower confidence)
        long bulletCount = aiResponse.lines().filter(l -> l.strip().startsWith("-") || l.strip().startsWith("*")).count();
        double heuristic = 1.0 / (1 + bulletCount * 0.1);
        return Math.round(heuristic * 100.0) / 100.0; // round to 2 decimals
    }

    private Integer estimateTokens(String text) {
        // Rough estimation: 1 token ≈ 4 characters
        return Math.max(text.length() / 4, 100);
    }
}
