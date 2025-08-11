package com.smartshopai.ai.analysis.presentation.controller;

import com.smartshopai.ai.analysis.application.service.AIAnalysisService;
import com.smartshopai.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/ai/analysis")
@RequiredArgsConstructor
public class AIAnalysisController {
    
    private final AIAnalysisService aiAnalysisService;
    
    @PostMapping("/product")
    public ResponseEntity<BaseResponse<String>> analyzeProduct(@RequestBody String productData) {
        log.info("Received product analysis request");
        
        try {
            String analysis = aiAnalysisService.analyzeProduct(productData);
            return ResponseEntity.ok(BaseResponse.success(analysis, "Product analysis completed successfully"));
            
        } catch (Exception e) {
            log.error("Error during product analysis: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(BaseResponse.error("Product analysis failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/compare")
    public ResponseEntity<BaseResponse<String>> compareProducts(@RequestBody List<String> productDataList) {
        log.info("Received product comparison request for {} products", productDataList.size());
        
        try {
            String comparison = aiAnalysisService.compareProducts(productDataList);
            return ResponseEntity.ok(BaseResponse.success(comparison, "Product comparison completed successfully"));
            
        } catch (Exception e) {
            log.error("Error during product comparison: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(BaseResponse.error("Product comparison failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/recommendations")
    public ResponseEntity<BaseResponse<String>> generateRecommendations(
            @RequestParam String userPreferences,
            @RequestBody String productData) {
        log.info("Received recommendation generation request");
        
        try {
            String recommendations = aiAnalysisService.generateRecommendations(userPreferences, productData);
            return ResponseEntity.ok(BaseResponse.success(recommendations, "Recommendations generated successfully"));
            
        } catch (Exception e) {
            log.error("Error during recommendation generation: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(BaseResponse.error("Recommendation generation failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/price-trends")
    public ResponseEntity<BaseResponse<String>> analyzePriceTrends(@RequestBody Map<String, Object> priceData) {
        log.info("Received price trend analysis request");
        
        try {
            String analysis = aiAnalysisService.analyzePriceTrends(priceData);
            return ResponseEntity.ok(BaseResponse.success(analysis, "Price trend analysis completed successfully"));
            
        } catch (Exception e) {
            log.error("Error during price trend analysis: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(BaseResponse.error("Price trend analysis failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<BaseResponse<String>> health() {
        return ResponseEntity.ok(BaseResponse.success("AI Analysis Service is running", "Service is healthy"));
    }
}
