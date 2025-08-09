package com.smartshopai.ai.recommendation.domain.service;

import com.smartshopai.ai.recommendation.domain.entity.RecommendationRequest;
import com.smartshopai.ai.recommendation.domain.entity.RecommendationResult;
import com.smartshopai.ai.recommendation.domain.repository.RecommendationRequestRepository;
import com.smartshopai.ai.recommendation.domain.repository.RecommendationResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of AIRecommendationService
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AIRecommendationServiceImpl implements AIRecommendationService {

    private final RecommendationRequestRepository recommendationRequestRepository;
    private final RecommendationResultRepository recommendationResultRepository;

    @Override
    public RecommendationRequest createRecommendationRequest(RecommendationRequest request) {
        log.info("Creating recommendation request for userId: {}", request.getUserId());
        
        // Validate request data
        validateRequestData(request);
        
        // Set default values
        request.setId(UUID.randomUUID().toString());
        request.setStatus("PENDING");
        request.setAiAnalysisCompleted(false);
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());
        
        RecommendationRequest savedRequest = recommendationRequestRepository.save(request);
        log.info("Recommendation request created successfully with ID: {}", savedRequest.getId());
        
        return savedRequest;
    }

    @Override
    @Cacheable(value = "recommendationRequests", key = "#id")
    public RecommendationRequest findRequestById(String id) {
        log.debug("Finding recommendation request by ID: {}", id);
        return recommendationRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation request not found"));
    }

    @Override
    public List<RecommendationRequest> findRequestsByUserId(String userId) {
        log.debug("Finding recommendation requests for userId: {}", userId);
        return recommendationRequestRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRequest> findRequestsByStatus(String status) {
        log.debug("Finding recommendation requests by status: {}", status);
        return recommendationRequestRepository.findByStatus(status);
    }

    @Override
    public RecommendationResult createRecommendationResult(RecommendationResult result) {
        log.info("Creating recommendation result for requestId: {}", result.getRecommendationRequestId());
        
        result.setId(UUID.randomUUID().toString());
        result.setStatus("ACTIVE");
        result.setAiAnalysisCompleted(true);
        result.setCreatedAt(LocalDateTime.now());
        result.setUpdatedAt(LocalDateTime.now());
        
        RecommendationResult savedResult = recommendationResultRepository.save(result);
        log.info("Recommendation result created successfully with ID: {}", savedResult.getId());
        
        return savedResult;
    }

    @Override
    @Cacheable(value = "recommendationResults", key = "#id")
    public RecommendationResult findResultById(String id) {
        log.debug("Finding recommendation result by ID: {}", id);
        return recommendationResultRepository.findById(id)
                .orElseThrow(() -> new com.smartshopai.common.exception.NotFoundException("Recommendation result not found"));
    }

    @Override
    public Optional<RecommendationResult> findResultByRequestId(String requestId) {
        log.debug("Finding recommendation result by requestId: {}", requestId);
        return recommendationResultRepository.findByRecommendationRequestId(requestId);
    }

    @Override
    public List<RecommendationResult> findResultsByUserId(String userId) {
        log.debug("Finding recommendation results for userId: {}", userId);
        return recommendationResultRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationResult> findActiveResults() {
        log.debug("Finding active recommendation results");
        return recommendationResultRepository.findByStatus("ACTIVE");
    }

    @Override
    public List<RecommendationResult> findPersonalizedResults() {
        log.debug("Finding personalized recommendation results");
        return recommendationResultRepository.findByPersonalized(true);
    }

    @Override
    @CacheEvict(value = "recommendationRequests", key = "#request.id")
    public RecommendationRequest updateRequestStatus(RecommendationRequest request, String status) {
        log.info("Updating recommendation request status to: {}", status);
        
        request.setStatus(status);
        request.setUpdatedAt(LocalDateTime.now());
        
        if ("COMPLETED".equals(status)) {
            request.setProcessedAt(LocalDateTime.now());
        }
        
        RecommendationRequest updatedRequest = recommendationRequestRepository.save(request);
        log.info("Recommendation request status updated successfully");
        
        return updatedRequest;
    }

    @Override
    @CacheEvict(value = "recommendationResults", key = "#result.id")
    public RecommendationResult updateResultStatus(RecommendationResult result, String status) {
        log.info("Updating recommendation result status to: {}", status);
        
        result.setStatus(status);
        result.setUpdatedAt(LocalDateTime.now());
        
        RecommendationResult updatedResult = recommendationResultRepository.save(result);
        log.info("Recommendation result status updated successfully");
        
        return updatedResult;
    }

    private void validateRequestData(RecommendationRequest request) {
        if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
            throw new com.smartshopai.common.exception.ValidationException("User ID is required");
        }
        
        if (request.getRequestType() == null || request.getRequestType().trim().isEmpty()) {
            throw new com.smartshopai.common.exception.ValidationException("Request type is required");
        }
        
        if (request.getContext() == null || request.getContext().trim().isEmpty()) {
            throw new com.smartshopai.common.exception.ValidationException("Context is required");
        }
    }
}
