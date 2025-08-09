package com.smartshopai.ai.recommendation.domain.service;

import com.smartshopai.ai.recommendation.domain.entity.Recommendation;

import java.util.List;

/**
 * Domain service for AI recommendation operations
 */
public interface RecommendationService {

    /**
     * Generate recommendations for a user
     */
    Recommendation generateRecommendations(Recommendation recommendation);

    /**
     * Get user recommendations
     */
    List<Recommendation> getUserRecommendations(String userId);

    /**
     * Get similar products
     */
    Recommendation getSimilarProducts(String productId);

    /**
     * Get trending products
     */
    Recommendation getTrendingProducts();

    /**
     * Get deal recommendations
     */
    Recommendation getDealRecommendations();

    /**
     * Delete recommendation
     */
    void deleteRecommendation(String recommendationId);
}
