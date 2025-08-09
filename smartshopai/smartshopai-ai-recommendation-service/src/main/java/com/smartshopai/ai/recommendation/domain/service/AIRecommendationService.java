package com.smartshopai.ai.recommendation.domain.service;

import com.smartshopai.ai.recommendation.domain.entity.RecommendationRequest;
import com.smartshopai.ai.recommendation.domain.entity.RecommendationResult;

import java.util.List;
import java.util.Optional;

/**
 * Domain service for AI recommendation operations
 */
public interface AIRecommendationService {

    /**
     * Create recommendation request
     */
    RecommendationRequest createRecommendationRequest(RecommendationRequest request);

    /**
     * Find recommendation request by ID
     */
    RecommendationRequest findRequestById(String id);

    /**
     * Find recommendation requests by user ID
     */
    List<RecommendationRequest> findRequestsByUserId(String userId);

    /**
     * Find recommendation requests by status
     */
    List<RecommendationRequest> findRequestsByStatus(String status);

    /**
     * Create recommendation result
     */
    RecommendationResult createRecommendationResult(RecommendationResult result);

    /**
     * Find recommendation result by ID
     */
    RecommendationResult findResultById(String id);

    /**
     * Find recommendation result by request ID
     */
    Optional<RecommendationResult> findResultByRequestId(String requestId);

    /**
     * Find recommendation results by user ID
     */
    List<RecommendationResult> findResultsByUserId(String userId);

    /**
     * Find active recommendation results
     */
    List<RecommendationResult> findActiveResults();

    /**
     * Find personalized recommendation results
     */
    List<RecommendationResult> findPersonalizedResults();

    /**
     * Update recommendation request status
     */
    RecommendationRequest updateRequestStatus(RecommendationRequest request, String status);

    /**
     * Update recommendation result status
     */
    RecommendationResult updateResultStatus(RecommendationResult result, String status);
}
