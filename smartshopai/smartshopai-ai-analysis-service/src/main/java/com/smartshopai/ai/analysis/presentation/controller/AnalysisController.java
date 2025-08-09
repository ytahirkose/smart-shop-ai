package com.smartshopai.ai.analysis.presentation.controller;

import com.smartshopai.ai.analysis.application.dto.request.CreateAnalysisRequest;
import com.smartshopai.ai.analysis.application.dto.response.AnalysisResponse;
import com.smartshopai.ai.analysis.application.dto.response.ComparisonResponse;
import com.smartshopai.ai.analysis.application.service.AIAnalysisApplicationService;
import com.smartshopai.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for AI Analysis operations
 * Provides endpoints for product analysis and comparison
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
@Tag(name = "AI Analysis", description = "AI-powered product analysis operations")
public class AnalysisController {
    
    private final AIAnalysisApplicationService aiAnalysisApplicationService;
    
    /**
     * Create a new product analysis
     */
    @PostMapping("/analyze")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Analyze product with AI", description = "Performs AI analysis on a product")
    public ResponseEntity<BaseResponse<AnalysisResponse>> analyzeProduct(
            @Valid @RequestBody CreateAnalysisRequest request) {
        log.info("Starting AI analysis for product: {}", request.getProductId());
        
        AnalysisResponse response = aiAnalysisApplicationService.createAnalysis(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(response, "Product analysis completed successfully"));
    }
    
    /**
     * Compare products using AI
     */
    @PostMapping("/compare")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Compare products with AI", description = "Compares multiple products using AI")
    public ResponseEntity<BaseResponse<ComparisonResponse>> compareProducts(
            @Valid @RequestBody CreateAnalysisRequest request) {
        log.info("Starting AI comparison for products: {}", request.getProductData());
        
        ComparisonResponse response = aiAnalysisApplicationService.compareProducts(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(response, "Product comparison completed successfully"));
    }
    
    /**
     * Get analysis by ID
     */
    @GetMapping("/{analysisId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get analysis result", description = "Retrieves analysis result by ID")
    public ResponseEntity<BaseResponse<AnalysisResponse>> getAnalysisResult(
            @PathVariable String analysisId) {
        log.debug("Getting analysis result for ID: {}", analysisId);
        
        AnalysisResponse response = aiAnalysisApplicationService.getAnalysisById(analysisId);
        
        return ResponseEntity.ok(BaseResponse.success(response));
    }
    
    /**
     * Get all analyses for a product
     */
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get product analyses", description = "Retrieves all analyses for a product")
    public ResponseEntity<BaseResponse<List<AnalysisResponse>>> getAnalysesByProductId(
            @PathVariable String productId) {
        log.info("Getting analyses for product: {}", productId);
        
        List<AnalysisResponse> responses = aiAnalysisApplicationService.getAnalysesByProductId(productId);
        return ResponseEntity.ok(BaseResponse.success(responses, "Analyses retrieved successfully"));
    }
    
    /**
     * Get active analyses for a product
     */
    @GetMapping("/product/{productId}/active")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get active product analyses", description = "Retrieves active analyses for a product")
    public ResponseEntity<BaseResponse<List<AnalysisResponse>>> getActiveAnalysesByProductId(
            @PathVariable String productId) {
        log.info("Getting active analyses for product: {}", productId);
        
        List<AnalysisResponse> responses = aiAnalysisApplicationService.getActiveAnalysesByProductId(productId);
        return ResponseEntity.ok(BaseResponse.success(responses, "Active analyses retrieved successfully"));
    }
    
    /**
     * Get analyses by type
     */
    @GetMapping("/type/{analysisType}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get analyses by type", description = "Retrieves analyses by analysis type")
    public ResponseEntity<BaseResponse<List<AnalysisResponse>>> getAnalysesByType(
            @PathVariable String analysisType) {
        log.info("Getting analyses by type: {}", analysisType);
        
        List<AnalysisResponse> responses = aiAnalysisApplicationService.getAnalysesByType(analysisType);
        return ResponseEntity.ok(BaseResponse.success(responses, "Analyses retrieved successfully"));
    }
    
    /**
     * Get user analysis history
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get user analysis history", description = "Retrieves analysis history for a user")
    public ResponseEntity<BaseResponse<List<AnalysisResponse>>> getUserAnalysisHistory(
            @PathVariable String userId) {
        log.debug("Getting analysis history for user: {}", userId);
        
        List<AnalysisResponse> responses = aiAnalysisApplicationService.getUserAnalysisHistory(userId);
        return ResponseEntity.ok(BaseResponse.success(responses, "User analysis history retrieved successfully"));
    }
    
    /**
     * Delete analysis
     */
    @DeleteMapping("/{analysisId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete analysis result", description = "Deletes an analysis result")
    public ResponseEntity<BaseResponse<Void>> deleteAnalysis(
            @PathVariable String analysisId) {
        log.info("Deleting analysis: {}", analysisId);
        
        aiAnalysisApplicationService.deleteAnalysis(analysisId);
        return ResponseEntity.ok(BaseResponse.success(null, "Analysis deleted successfully"));
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "AI service health check", description = "Checks if AI service is healthy")
    public ResponseEntity<BaseResponse<String>> healthCheck() {
        log.debug("Health check requested");
        
        String status = "AI Analysis Service is healthy";
        return ResponseEntity.ok(BaseResponse.success(status));
    }
}
