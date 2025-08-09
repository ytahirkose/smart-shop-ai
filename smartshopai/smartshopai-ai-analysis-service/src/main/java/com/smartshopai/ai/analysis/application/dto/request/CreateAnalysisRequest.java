package com.smartshopai.ai.analysis.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

/**
 * Request DTO for creating product analysis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnalysisRequest {
    
    @NotBlank(message = "Product ID is required")
    private String productId;
    
    @NotBlank(message = "Analysis type is required")
    private String analysisType; // technical, price, quality, comparison
    
    @NotNull(message = "Product data is required")
    private Map<String, Object> productData;
    
    private Map<String, Object> analysisOptions;
    private String userId;
    private String requestId;
}
