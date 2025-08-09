package com.smartshopai.ai.recommendation.presentation.controller;

import com.smartshopai.ai.recommendation.application.dto.request.CreateRecommendationRequest;
import com.smartshopai.ai.recommendation.application.dto.request.GetRecommendationsRequest;
import com.smartshopai.ai.recommendation.application.dto.request.UpdateUserPreferencesRequest;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationResponse;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationResultResponse;
import com.smartshopai.ai.recommendation.application.service.RecommendationApplicationService;
import com.smartshopai.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for AI recommendation operations
 */
@Slf4j
@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationApplicationService recommendationApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<RecommendationResponse>> createRecommendation(
            @Valid @RequestBody CreateRecommendationRequest request) {
        log.info("Creating recommendation for user: {}", request.getUserId());
        
        RecommendationResponse response = recommendationApplicationService.createRecommendation(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<RecommendationResultResponse>>> getRecommendations(
            @Valid @RequestBody GetRecommendationsRequest request) {
        log.info("Getting recommendations for user: {}", request.getUserId());
        
        List<RecommendationResultResponse> response = recommendationApplicationService.getRecommendations(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PutMapping("/preferences")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<String>> updateUserPreferences(
            @Valid @RequestBody UpdateUserPreferencesRequest request) {
        log.info("Updating preferences for user: {}", request.getUserId());
        
        recommendationApplicationService.updateUserPreferences(request);
        return ResponseEntity.ok(BaseResponse.success("Preferences updated successfully"));
    }

    @GetMapping("/personalized/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<RecommendationResultResponse>>> getPersonalizedRecommendations(
            @PathVariable String userId) {
        log.debug("Getting personalized recommendations for user: {}", userId);
        
        List<RecommendationResultResponse> response = recommendationApplicationService.getPersonalizedRecommendations(userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/trending")
    public ResponseEntity<BaseResponse<List<RecommendationResultResponse>>> getTrendingRecommendations(
            @RequestParam(required = false) String category) {
        log.debug("Getting trending recommendations for category: {}", category);
        
        List<RecommendationResultResponse> response = recommendationApplicationService.getTrendingRecommendations(category);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/similar/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<RecommendationResultResponse>>> getSimilarProductRecommendations(
            @PathVariable String productId, @RequestParam String userId) {
        log.debug("Getting similar product recommendations for product: {} and user: {}", productId, userId);
        
        List<RecommendationResultResponse> response = recommendationApplicationService.getSimilarProductRecommendations(productId, userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/alternatives/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<RecommendationResultResponse>>> getAlternativeRecommendations(
            @PathVariable String productId, @RequestParam String userId) {
        log.debug("Getting alternative recommendations for product: {} and user: {}", productId, userId);
        
        List<RecommendationResultResponse> response = recommendationApplicationService.getAlternativeRecommendations(productId, userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/track")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<String>> trackUserInteraction(
            @RequestParam String userId, @RequestParam String productId, @RequestParam String interactionType) {
        log.debug("Tracking user interaction - userId: {}, productId: {}, type: {}", userId, productId, interactionType);
        
        recommendationApplicationService.trackUserInteraction(userId, productId, interactionType);
        return ResponseEntity.ok(BaseResponse.success("Interaction tracked successfully"));
    }

    @PostMapping("/refresh/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<String>> refreshRecommendations(@PathVariable String userId) {
        log.info("Refreshing recommendations for user: {}", userId);
        
        recommendationApplicationService.refreshRecommendations(userId);
        return ResponseEntity.ok(BaseResponse.success("Recommendations refreshed successfully"));
    }
}
