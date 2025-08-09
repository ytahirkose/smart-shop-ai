package com.smartshopai.ai.analysis.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Request DTO for AI analysis operations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRequest {
    
    @NotBlank(message = "Product ID is required")
    private String productId;
    
    private List<String> productIds; // For comparison operations
    
    @NotBlank(message = "Request type is required")
    private String requestType; // analyze, compare, technical, price, quality
    
    @NotBlank(message = "Prompt is required")
    private String prompt;
    
    private String userId;
    
    private String requestId;
    
    private Map<String, Object> analysisOptions;
    
    private Map<String, Object> productData;
}
