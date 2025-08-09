package com.smartshopai.ai.analysis.domain.service;

import com.smartshopai.ai.analysis.domain.entity.AnalysisRequest;
import com.smartshopai.ai.analysis.domain.entity.AnalysisResult;
import com.smartshopai.ai.analysis.domain.entity.ProductComparison;
import com.smartshopai.ai.analysis.domain.repository.AnalysisRequestRepository;
import com.smartshopai.ai.analysis.domain.repository.AnalysisResultRepository;
import com.smartshopai.ai.analysis.domain.repository.ProductComparisonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import com.smartshopai.common.exception.BusinessException;
import com.smartshopai.common.exception.NotFoundException;
import com.smartshopai.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of AIAnalysisService
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AIAnalysisServiceImpl implements AIAnalysisService {

    private final AnalysisRequestRepository analysisRequestRepository;
    private final AnalysisResultRepository analysisResultRepository;
    private final ProductComparisonRepository productComparisonRepository;
    private final AnalysisEngineService analysisEngineService;

    @Override
    public AnalysisRequest createAnalysisRequest(AnalysisRequest request) {
        log.info("Creating analysis request for userId: {}", request.getUserId());
        
        // Validate request data
        validateRequestData(request);
        
        // Set default values
        request.setId(UUID.randomUUID().toString());
        request.setStatus("PENDING");
        request.setAiAnalysisCompleted(false);
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());
        
        AnalysisRequest savedRequest = analysisRequestRepository.save(request);
        log.info("Analysis request created successfully with ID: {}", savedRequest.getId());
        
        return savedRequest;
    }

    @Override
    public AnalysisResult analyzeProduct(String productId, String userId, String prompt) {
        log.info("Analyzing product: {} for user: {}", productId, userId);
        
        try {
            // Create analysis request
            AnalysisRequest request = AnalysisRequest.builder()
                    .userId(userId)
                    .productId(productId)
                    .analysisType("PRODUCT_ANALYSIS")
                    .prompt(prompt)
                    .status("PENDING")
                    .aiAnalysisCompleted(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            AnalysisRequest savedRequest = analysisRequestRepository.save(request);
            
            // Perform AI analysis
            AnalysisResult result = analysisEngineService.performProductAnalysis(savedRequest);
            
            // Update request status
            savedRequest.setStatus("COMPLETED");
            savedRequest.setAiAnalysisCompleted(true);
            savedRequest.setProcessedAt(LocalDateTime.now());
            analysisRequestRepository.save(savedRequest);
            
            // Save analysis result
            AnalysisResult savedResult = analysisResultRepository.save(result);
            log.info("Product analysis completed successfully with ID: {}", savedResult.getId());
            
            return savedResult;
            
        } catch (Exception e) {
            log.error("Error analyzing product: {}", productId, e);
            throw new BusinessException("Product analysis failed", e);
        }
    }

    @Override
    public ProductComparison compareProducts(String userId, String requestId, List<String> productIds) {
        log.info("Comparing products for user: {} with request: {}", userId, requestId);
        
        try {
            // Create analysis request for comparison
            AnalysisRequest request = AnalysisRequest.builder()
                    .userId(userId)
                    .requestId(requestId)
                    .analysisType("PRODUCT_COMPARISON")
                    .productIds(productIds)
                    .status("PENDING")
                    .aiAnalysisCompleted(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            AnalysisRequest savedRequest = analysisRequestRepository.save(request);
            
            // Perform AI comparison
            ProductComparison comparison = analysisEngineService.performProductComparison(savedRequest);
            
            // Update request status
            savedRequest.setStatus("COMPLETED");
            savedRequest.setAiAnalysisCompleted(true);
            savedRequest.setProcessedAt(LocalDateTime.now());
            analysisRequestRepository.save(savedRequest);
            
            // Save comparison result
            ProductComparison savedComparison = productComparisonRepository.save(comparison);
            log.info("Product comparison completed successfully with ID: {}", savedComparison.getId());
            
            return savedComparison;
            
        } catch (Exception e) {
            log.error("Error comparing products for request: {}", requestId, e);
            throw new BusinessException("Product comparison failed", e);
        }
    }

    @Override
    @Cacheable(value = "analysisResults", key = "#analysisId")
    public AnalysisResult getAnalysisResult(String analysisId) {
        log.debug("Getting analysis result: {}", analysisId);
        return analysisResultRepository.findById(analysisId)
                .orElseThrow(() -> new NotFoundException("Analysis result not found"));
    }

    @Override
    public List<AnalysisRequest> getUserAnalysisRequests(String userId) {
        log.debug("Getting analysis requests for user: {}", userId);
        return analysisRequestRepository.findByUserId(userId);
    }

    @Override
    @CacheEvict(value = "analysisRequests", key = "#requestId")
    public void updateAnalysisStatus(String requestId, String status) {
        log.info("Updating analysis request status to: {}", status);
        
        AnalysisRequest request = analysisRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Analysis request not found"));
        
        request.setStatus(status);
        request.setUpdatedAt(LocalDateTime.now());
        
        if ("COMPLETED".equals(status)) {
            request.setProcessedAt(LocalDateTime.now());
        }
        
        analysisRequestRepository.save(request);
        log.info("Analysis request status updated successfully");
    }

    private void validateRequestData(AnalysisRequest request) {
        if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
            throw new ValidationException("User ID is required");
        }
        
        if (request.getAnalysisType() == null || request.getAnalysisType().trim().isEmpty()) {
            throw new ValidationException("Analysis type is required");
        }
        
        if (request.getProductId() == null || request.getProductId().trim().isEmpty()) {
            throw new ValidationException("Product ID is required");
        }
    }
}
