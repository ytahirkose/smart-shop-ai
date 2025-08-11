package com.smartshopai.airecommendation.domain.service;

import com.smartshopai.airecommendation.infrastructure.dto.ProductAnalysisDto;
import com.smartshopai.airecommendation.infrastructure.dto.UserBehaviorMetricsDto;
import com.smartshopai.airecommendation.infrastructure.dto.UserDto;
import com.smartshopai.airecommendation.infrastructure.dto.UserPreferencesDto;
import com.smartshopai.airecommendation.infrastructure.client.AnalysisServiceClient;
import com.smartshopai.airecommendation.infrastructure.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendationService {

    private final UserServiceClient userServiceClient;
    private final AnalysisServiceClient analysisServiceClient;

    /**
     * Hiper-kişiselleştirilmiş öneri üret (embedding, prompt, stream, koleksiyon kullanımı)
     */
    public String getPersonalizedRecommendations(String userId, Set<String> roles) {
        log.info("Generating HYPER-PERSONALIZED recommendations for user ID: {} with roles: {}", userId, roles);
        boolean isPremiumUser = roles.contains("ROLE_PREMIUM");
        int topK = isPremiumUser ? 10 : 5;

        // Kullanıcı ve davranış verilerini çek
        UserDto user = userServiceClient.getUserById(userId);
        String userPreferencesText = convertPreferencesToString(user);
        String userBehaviorText = convertBehaviorToString(user);
        String combinedInfo = userPreferencesText + " " + userBehaviorText;

        // Mock embedding oluştur - will be replaced with real AI when Spring AI is ready
        List<Double> userEmbedding = generateMockEmbedding(combinedInfo);
        log.info("Generated combined embedding for user ID: {}", userId);

        // Benzer ürünleri bul
        List<ProductAnalysisDto> similarProducts = analysisServiceClient.findSimilarProducts(userEmbedding, topK);
        if (similarProducts.isEmpty()) {
            return "We are still getting to know you! Based on your profile and activity, we couldn't find a perfect match yet. Please add more preferences or browse some products for better recommendations.";
        }
        String similarProductIds = similarProducts.stream()
                .map(ProductAnalysisDto::getProductId)
                .collect(Collectors.joining(", "));

        // Mock AI response - will be replaced with real AI when Spring AI is ready
        return generateMockRecommendation(userPreferencesText, userBehaviorText, similarProductIds, isPremiumUser);
    }

    private String convertPreferencesToString(UserDto user) {
        if (user == null || user.getUserPreferences() == null) {
            return "A new user with no specified preferences.";
        }
        UserPreferencesDto prefs = user.getUserPreferences();
        return String.format(
                "Likes categories: %s. Preferred brands: %s. Budget is around %s.",
                prefs.getPreferredCategories(),
                prefs.getPreferredBrands(),
                prefs.getBudgetLimit()
        );
    }

    private String convertBehaviorToString(UserDto user) {
        if (user == null || user.getUserBehaviorMetrics() == null) {
            return "No recent activity recorded.";
        }
        UserBehaviorMetricsDto metrics = user.getUserBehaviorMetrics();
        return String.format(
                "Recently viewed products (IDs): %s. Recent search queries: '%s'.",
                metrics.getRecentViewedProductIds(),
                metrics.getRecentSearchQueries()
        );
    }

    private List<Double> generateMockEmbedding(String text) {
        // Mock embedding - will be replaced with real AI when Spring AI is ready
        List<Double> embedding = new java.util.ArrayList<>();
        for (int i = 0; i < 768; i++) {
            embedding.add(Math.random());
        }
        return embedding;
    }

    private String generateMockRecommendation(String userPreferences, String userBehavior, String productIds, boolean isPremiumUser) {
        String selectionCount = isPremiumUser ? "3-4" : "1-2";
        
        return String.format("""
            **SMARTSHOPAI PERSONALIZED RECOMMENDATIONS**
            
            Hello! I'm SmartShopAI, your hyper-intelligent shopping assistant.
            
            **Your Stated Preferences**: %s
            **Your Recent Behavior**: %s
            
            **Recommended Products**: %s
            
            **Why I'm Recommending These**:
            Based on your preferences and recent activity, I've found products that perfectly match your profile. 
            I noticed some interesting patterns in your behavior that suggest you might love these selections.
            
            **Top %s Products to Highlight**:
            - Product 1: Perfect match for your stated preferences
            - Product 2: Based on your recent browsing behavior
            - Product 3: Combines both your preferences and behavior patterns
            
            These recommendations are tailored specifically for you, combining your stated preferences with insights from your recent activity.
            """, userPreferences, userBehavior, productIds, selectionCount);
    }
}
