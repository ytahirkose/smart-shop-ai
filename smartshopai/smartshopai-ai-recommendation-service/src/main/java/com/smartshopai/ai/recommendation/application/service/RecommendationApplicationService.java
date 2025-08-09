package com.smartshopai.ai.recommendation.application.service;

import com.smartshopai.ai.recommendation.application.dto.request.CreateRecommendationRequest;
import com.smartshopai.ai.recommendation.application.dto.request.GetRecommendationsRequest;
import com.smartshopai.ai.recommendation.application.dto.request.UpdateUserPreferencesRequest;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationResponse;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationResultResponse;
import com.smartshopai.ai.recommendation.application.mapper.RecommendationMapper;
import com.smartshopai.ai.recommendation.domain.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service for recommendation operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationApplicationService {

    private final RecommendationService recommendationService;
    private final RecommendationMapper recommendationMapper;

    public RecommendationResponse createRecommendation(CreateRecommendationRequest request) {
        log.info("Creating recommendation for user: {}", request.getUserId());
        
        // Adapt to domain model: map request into Recommendation aggregate
        com.smartshopai.ai.recommendation.domain.entity.Recommendation recommendation =
                com.smartshopai.ai.recommendation.domain.entity.Recommendation.builder()
                        .userId(request.getUserId())
                        .requestType(request.getRequestType())
                        .context(request.getContext())
                        .preferredCategories(request.getPreferredCategories())
                        .preferredBrands(request.getPreferredBrands())
                        .maxBudget(request.getMaxBudget())
                        .shoppingPreferences(request.getShoppingPreferences())
                        .aiModel(request.getAiModel())
                        .modelParameters(request.getModelParameters())
                        .prompt(request.getPrompt())
                        .limit(request.getLimit())
                        .build();

        com.smartshopai.ai.recommendation.domain.entity.Recommendation saved = recommendationService.generateRecommendations(recommendation);
        return recommendationMapper.toResponse(saved);
    }

    private RecommendationResultResponse toResultResponse(com.smartshopai.ai.recommendation.domain.entity.Recommendation rec, String userId, String requestType, String context, boolean personalized) {
        return RecommendationResultResponse.builder()
                .id(rec.getId())
                .userId(userId != null ? userId : rec.getUserId())
                .requestType(requestType != null ? requestType : rec.getRequestType())
                .context(context != null ? context : rec.getContext())
                .recommendationSummary(rec.getRecommendationSummary())
                .reasoning(rec.getReasoning())
                .confidenceScore(rec.getConfidenceScore())
                .aiModel(rec.getAiModelUsed())
                .tokensUsed(rec.getTokensUsed() == null ? null : rec.getTokensUsed().longValue())
                .processingTimeMs(rec.getProcessingTimeMs())
                .createdAt(rec.getCreatedAt())
                .updatedAt(rec.getUpdatedAt())
                .status("ACTIVE")
                .personalized(personalized)
                .aiAnalysisCompleted(true)
                .recommendedProducts(null)
                .build();
    }

    public List<RecommendationResultResponse> getRecommendations(GetRecommendationsRequest request) {
        log.info("Getting recommendations for user: {}", request.getUserId());
        List<com.smartshopai.ai.recommendation.domain.entity.Recommendation> recs = recommendationService.getUserRecommendations(request.getUserId());
        return recs.stream()
                .map(r -> toResultResponse(r, null, null, null, true))
                .toList();
    }

    public void updateUserPreferences(UpdateUserPreferencesRequest request) {
        log.info("Updating preferences for user: {}", request.getUserId());
        
        // For now, this could persist preferences via a dedicated service; placeholder no-op
        log.info("User preferences update accepted for userId: {}", request.getUserId());
    }

    public List<RecommendationResultResponse> getPersonalizedRecommendations(String userId) {
        log.debug("Getting personalized recommendations for user: {}", userId);
        List<com.smartshopai.ai.recommendation.domain.entity.Recommendation> recs = recommendationService.getUserRecommendations(userId);
        return recs.stream()
                .map(r -> toResultResponse(r, null, null, null, true))
                .toList();
    }

    public List<RecommendationResultResponse> getTrendingRecommendations(String category) {
        log.debug("Getting trending recommendations for category: {}", category);
        com.smartshopai.ai.recommendation.domain.entity.Recommendation rec = recommendationService.getTrendingProducts();
        return List.of(toResultResponse(rec, null, null, null, false));
    }

    public List<RecommendationResultResponse> getSimilarProductRecommendations(String productId, String userId) {
        log.debug("Getting similar product recommendations for product: {} and user: {}", productId, userId);
        com.smartshopai.ai.recommendation.domain.entity.Recommendation rec = recommendationService.getSimilarProducts(productId);
        return List.of(toResultResponse(rec, userId, "SIMILAR_PRODUCTS", "PRODUCT_SEARCH", false));
    }

    public List<RecommendationResultResponse> getAlternativeRecommendations(String productId, String userId) {
        log.debug("Getting alternative recommendations for product: {} and user: {}", productId, userId);
        com.smartshopai.ai.recommendation.domain.entity.Recommendation rec = recommendationService.getDealRecommendations();
        return List.of(toResultResponse(rec, userId, "ALTERNATIVE", "DISCOVERY", false));
    }

    public void trackUserInteraction(String userId, String productId, String interactionType) {
        log.debug("Tracking user interaction - userId: {}, productId: {}, type: {}", userId, productId, interactionType);
        
        // Placeholder no-op
    }

    public void refreshRecommendations(String userId) {
        log.info("Refreshing recommendations for user: {}", userId);
        
        // Placeholder no-op
    }
}
