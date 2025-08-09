package com.smartshopai.ai.recommendation.application.service;

import com.smartshopai.ai.recommendation.application.dto.request.CreateRecommendationRequest;
import com.smartshopai.ai.recommendation.application.dto.request.GetRecommendationsRequest;
import com.smartshopai.ai.recommendation.application.dto.request.UpdateUserPreferencesRequest;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationResponse;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationResultResponse;
import com.smartshopai.ai.recommendation.application.mapper.RecommendationMapper;
import com.smartshopai.ai.recommendation.domain.entity.RecommendationResult;
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

    public List<RecommendationResultResponse> getRecommendations(GetRecommendationsRequest request) {
        log.info("Getting recommendations for user: {}", request.getUserId());
        
        // Use simpler domain API for now: fetch stored recommendations for user
        List<com.smartshopai.ai.recommendation.domain.entity.Recommendation> recs =
                recommendationService.getUserRecommendations(request.getUserId());
        return recommendationMapper.toResponseList(recs).stream()
                .map(r -> RecommendationResultResponse.builder()
                        .id(r.getId())
                        .userId(r.getUserId())
                        .requestType(r.getRequestType())
                        .context(r.getContext())
                        .recommendationSummary(r.getRecommendationSummary())
                        .reasoning(r.getReasoning())
                        .confidenceScore(r.getConfidenceScore())
                        .aiModel(r.getAiModelUsed())
                        .tokensUsed(r.getTokensUsed() == null ? null : r.getTokensUsed().longValue())
                        .processingTimeMs(r.getProcessingTimeMs())
                        .createdAt(r.getCreatedAt())
                        .updatedAt(r.getUpdatedAt())
                        .status("ACTIVE")
                        .personalized(true)
                        .aiAnalysisCompleted(true)
                        .recommendedProducts(null)
                        .build())
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
        return recommendationMapper.toResponseList(recs).stream()
                .map(r -> RecommendationResultResponse.builder()
                        .id(r.getId())
                        .userId(r.getUserId())
                        .requestType(r.getRequestType())
                        .context(r.getContext())
                        .recommendationSummary(r.getRecommendationSummary())
                        .reasoning(r.getReasoning())
                        .confidenceScore(r.getConfidenceScore())
                        .aiModel(r.getAiModelUsed())
                        .tokensUsed(r.getTokensUsed() == null ? null : r.getTokensUsed().longValue())
                        .processingTimeMs(r.getProcessingTimeMs())
                        .createdAt(r.getCreatedAt())
                        .updatedAt(r.getUpdatedAt())
                        .status("ACTIVE")
                        .personalized(true)
                        .aiAnalysisCompleted(true)
                        .recommendedProducts(null)
                        .build())
                .toList();
    }

    public List<RecommendationResultResponse> getTrendingRecommendations(String category) {
        log.debug("Getting trending recommendations for category: {}", category);
        
        com.smartshopai.ai.recommendation.domain.entity.Recommendation rec = recommendationService.getTrendingProducts();
        return List.of(RecommendationResultResponse.builder()
                .id(rec.getId())
                .userId(rec.getUserId())
                .requestType(rec.getRequestType())
                .context(rec.getContext())
                .recommendationSummary(rec.getRecommendationSummary())
                .reasoning(rec.getReasoning())
                .confidenceScore(rec.getConfidenceScore())
                .aiModel(rec.getAiModelUsed())
                .tokensUsed(rec.getTokensUsed() == null ? null : rec.getTokensUsed().longValue())
                .processingTimeMs(rec.getProcessingTimeMs())
                .createdAt(rec.getCreatedAt())
                .updatedAt(rec.getUpdatedAt())
                .status("ACTIVE")
                .personalized(false)
                .aiAnalysisCompleted(true)
                .recommendedProducts(null)
                .build());
    }

    public List<RecommendationResultResponse> getSimilarProductRecommendations(String productId, String userId) {
        log.debug("Getting similar product recommendations for product: {} and user: {}", productId, userId);
        
        com.smartshopai.ai.recommendation.domain.entity.Recommendation rec = recommendationService.getSimilarProducts(productId);
        return List.of(RecommendationResultResponse.builder()
                .id(rec.getId())
                .userId(userId)
                .requestType("SIMILAR_PRODUCTS")
                .context("PRODUCT_SEARCH")
                .recommendationSummary(rec.getRecommendationSummary())
                .reasoning(rec.getReasoning())
                .confidenceScore(rec.getConfidenceScore())
                .aiModel(rec.getAiModelUsed())
                .tokensUsed(rec.getTokensUsed() == null ? null : rec.getTokensUsed().longValue())
                .processingTimeMs(rec.getProcessingTimeMs())
                .createdAt(rec.getCreatedAt())
                .updatedAt(rec.getUpdatedAt())
                .status("ACTIVE")
                .personalized(false)
                .aiAnalysisCompleted(true)
                .recommendedProducts(null)
                .build());
    }

    public List<RecommendationResultResponse> getAlternativeRecommendations(String productId, String userId) {
        log.debug("Getting alternative recommendations for product: {} and user: {}", productId, userId);
        
        com.smartshopai.ai.recommendation.domain.entity.Recommendation rec = recommendationService.getDealRecommendations();
        return List.of(RecommendationResultResponse.builder()
                .id(rec.getId())
                .userId(userId)
                .requestType("ALTERNATIVE")
                .context("DISCOVERY")
                .recommendationSummary(rec.getRecommendationSummary())
                .reasoning(rec.getReasoning())
                .confidenceScore(rec.getConfidenceScore())
                .aiModel(rec.getAiModelUsed())
                .tokensUsed(rec.getTokensUsed() == null ? null : rec.getTokensUsed().longValue())
                .processingTimeMs(rec.getProcessingTimeMs())
                .createdAt(rec.getCreatedAt())
                .updatedAt(rec.getUpdatedAt())
                .status("ACTIVE")
                .personalized(false)
                .aiAnalysisCompleted(true)
                .recommendedProducts(null)
                .build());
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
