package com.smartshopai.ai.recommendation.domain.service;

import com.smartshopai.ai.recommendation.domain.entity.Recommendation;
import com.smartshopai.ai.recommendation.domain.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of RecommendationService
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final AIRecommendationEngineService aiRecommendationEngineService;

    @Override
    public Recommendation generateRecommendations(Recommendation recommendation) {
        log.info("Generating recommendations for user: {}", recommendation.getUserId());
        
        // Set default values
        recommendation.setId(UUID.randomUUID().toString());
        recommendation.setCreatedAt(LocalDateTime.now());
        recommendation.setUpdatedAt(LocalDateTime.now());
        
        // Generate AI recommendations using Spring AI
        recommendation = aiRecommendationEngineService.generatePersonalizedRecommendations(recommendation);
        
        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        log.info("Recommendations generated successfully with ID: {}", savedRecommendation.getId());
        
        return savedRecommendation;
    }

    @Override
    public List<Recommendation> getUserRecommendations(String userId) {
        log.debug("Getting recommendations for user: {}", userId);
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public Recommendation getSimilarProducts(String productId) {
        log.debug("Getting similar products for: {}", productId);
        
        // Generate AI-powered similar products recommendation
        Recommendation recommendation = aiRecommendationEngineService.generateSimilarProductsRecommendation(productId, "system");
        
        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getTrendingProducts() {
        log.debug("Getting trending products");
        
        // Generate AI-powered trending products recommendation
        Recommendation recommendation = aiRecommendationEngineService.generateTrendingRecommendations();
        
        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getDealRecommendations() {
        log.debug("Getting deal recommendations");
        
        // Generate AI-powered deal recommendations
        Recommendation recommendation = aiRecommendationEngineService.generateDealRecommendations();
        
        return recommendationRepository.save(recommendation);
    }

    @Override
    public void deleteRecommendation(String recommendationId) {
        log.info("Deleting recommendation: {}", recommendationId);
        recommendationRepository.deleteById(recommendationId);
    }
}
