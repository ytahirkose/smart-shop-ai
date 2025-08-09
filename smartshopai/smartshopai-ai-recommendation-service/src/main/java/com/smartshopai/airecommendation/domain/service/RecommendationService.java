package com.smartshopai.airecommendation.domain.service;

import com.smartshopai.airecommendation.infrastructure.client.AnalysisServiceClient;
import com.smartshopai.airecommendation.infrastructure.client.UserServiceClient;
import com.smartshopai.airecommendation.infrastructure.dto.ProductAnalysisDto;
import com.smartshopai.airecommendation.infrastructure.dto.UserDto;
import com.smartshopai.airecommendation.infrastructure.dto.UserBehaviorMetricsDto;
import com.smartshopai.airecommendation.infrastructure.dto.UserPreferencesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
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
    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;

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

        // Embedding oluştur
        float[] rawEmb = embeddingModel.embed(combinedInfo);
        List<Double> userEmbedding = new java.util.ArrayList<>(rawEmb.length);
        for (float f : rawEmb) userEmbedding.add((double) f);
        log.info("Generated combined embedding for user ID: {}", userId);

        // Benzer ürünleri bul
        List<ProductAnalysisDto> similarProducts = analysisServiceClient.findSimilarProducts(userEmbedding, topK);
        if (similarProducts.isEmpty()) {
            return "We are still getting to know you! Based on your profile and activity, we couldn't find a perfect match yet. Please add more preferences or browse some products for better recommendations.";
        }
        String similarProductIds = similarProducts.stream()
                .map(ProductAnalysisDto::getProductId)
                .collect(Collectors.joining(", "));

        // Prompt oluştur ve LLM'e gönder
        String promptString = """
            You are 'SmartShopAI', a hyper-intelligent and perceptive shopping assistant.
            You have deep knowledge about a user's stated preferences AND their recent behavior. Your task is to synthesize this information into a truly personal and insightful recommendation.

            Here's the user's data:
            - STATED PREFERENCES: {userPreferences}
            - RECENT BEHAVIOR: {userBehavior}
            
            And here are the products that are technically the closest match to this combined profile: {productIds}

            Your mission:
            1.  Analyze both preferences and behavior. Notice any interesting patterns or contradictions (e.g., user says they like 'budget' items but looks at 'premium' products).
            2.  Write a short, engaging recommendation. Address the user directly.
            3.  Explicitly mention WHY you are recommending these products, referencing BOTH their preferences and recent actions. For example: "I know you prefer [preference], but I saw you were looking at [behavior], so you might also love this..."
            4.  Select the top {selectionCount} products from the list to highlight.
            """;
        PromptTemplate promptTemplate = new PromptTemplate(promptString);
        Prompt prompt = promptTemplate.create(Map.of(
                "userPreferences", Objects.toString(userPreferencesText, "No specific preferences provided."),
                "userBehavior", Objects.toString(userBehaviorText, "No recent activity recorded."),
                "productIds", Objects.toString(similarProductIds, "None"),
                "selectionCount", isPremiumUser ? "3-4" : "1-2"
        ));
        log.info("Sending final, behavior-driven prompt to LLM for user ID: {}", userId);
        return chatModel.call(prompt).getResult().getOutput().getContent();
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
}
