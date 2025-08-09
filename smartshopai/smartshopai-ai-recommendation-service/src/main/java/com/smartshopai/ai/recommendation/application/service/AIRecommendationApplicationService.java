package com.smartshopai.ai.recommendation.application.service;

import com.smartshopai.ai.recommendation.application.dto.request.CreateRecommendationRequest;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationRequestResponse;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationResultResponse;
import com.smartshopai.ai.recommendation.application.mapper.RecommendationMapper;
import com.smartshopai.ai.recommendation.domain.entity.RecommendationRequest;
import com.smartshopai.ai.recommendation.domain.entity.RecommendationResult;
import com.smartshopai.ai.recommendation.domain.service.AIRecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AIRecommendationApplicationService {

    private final AIRecommendationService aiRecommendationService;
    private final RecommendationMapper recommendationMapper;

    public RecommendationRequestResponse createRecommendationRequest(CreateRecommendationRequest request) {
        log.info("Creating recommendation request for userId: {}", request.getUserId());
        
        RecommendationRequest entity = recommendationMapper.toEntity(request);
        RecommendationRequest savedRequest = aiRecommendationService.createRecommendationRequest(entity);
        
        return recommendationMapper.toRequestResponse(savedRequest);
    }

    public RecommendationRequestResponse getRecommendationRequest(String id) {
        log.debug("Getting recommendation request by ID: {}", id);
        
        RecommendationRequest request = aiRecommendationService.findRequestById(id);
        return recommendationMapper.toRequestResponse(request);
    }

    public List<RecommendationRequestResponse> getRecommendationRequestsByUserId(String userId) {
        log.debug("Getting recommendation requests for userId: {}", userId);
        
        List<RecommendationRequest> requests = aiRecommendationService.findRequestsByUserId(userId);
        return recommendationMapper.toRequestResponseList(requests);
    }

    public List<RecommendationRequestResponse> getRecommendationRequestsByStatus(String status) {
        log.debug("Getting recommendation requests by status: {}", status);
        
        List<RecommendationRequest> requests = aiRecommendationService.findRequestsByStatus(status);
        return recommendationMapper.toRequestResponseList(requests);
    }

    public RecommendationResultResponse getRecommendationResult(String id) {
        log.debug("Getting recommendation result by ID: {}", id);
        
        RecommendationResult result = aiRecommendationService.findResultById(id);
        return recommendationMapper.toResultResponse(result);
    }

    public Optional<RecommendationResultResponse> getRecommendationResultByRequestId(String requestId) {
        log.debug("Getting recommendation result by requestId: {}", requestId);
        
        Optional<RecommendationResult> result = aiRecommendationService.findResultByRequestId(requestId);
        return result.map(recommendationMapper::toResultResponse);
    }

    public List<RecommendationResultResponse> getRecommendationResultsByUserId(String userId) {
        log.debug("Getting recommendation results for userId: {}", userId);
        
        List<RecommendationResult> results = aiRecommendationService.findResultsByUserId(userId);
        return recommendationMapper.toResultResponseList(results);
    }

    public List<RecommendationResultResponse> getActiveRecommendationResults() {
        log.debug("Getting active recommendation results");
        
        List<RecommendationResult> results = aiRecommendationService.findActiveResults();
        return recommendationMapper.toResultResponseList(results);
    }

    public List<RecommendationResultResponse> getPersonalizedRecommendationResults() {
        log.debug("Getting personalized recommendation results");
        
        List<RecommendationResult> results = aiRecommendationService.findPersonalizedResults();
        return recommendationMapper.toResultResponseList(results);
    }

    public RecommendationRequestResponse updateRequestStatus(String id, String status) {
        log.info("Updating recommendation request status - id: {}, status: {}", id, status);
        
        RecommendationRequest request = aiRecommendationService.findRequestById(id);
        RecommendationRequest updatedRequest = aiRecommendationService.updateRequestStatus(request, status);
        
        return recommendationMapper.toRequestResponse(updatedRequest);
    }

    public RecommendationResultResponse updateResultStatus(String id, String status) {
        log.info("Updating recommendation result status - id: {}, status: {}", id, status);
        
        RecommendationResult result = aiRecommendationService.findResultById(id);
        RecommendationResult updatedResult = aiRecommendationService.updateResultStatus(result, status);
        
        return recommendationMapper.toResultResponse(updatedResult);
    }
}
