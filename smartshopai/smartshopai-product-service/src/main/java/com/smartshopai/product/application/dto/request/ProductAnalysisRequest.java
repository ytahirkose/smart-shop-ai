package com.smartshopai.product.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

/**
 * Request DTO for product analysis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAnalysisRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Analysis type is required")
    private String analysisType; // TECHNICAL, PRICE_QUALITY, COMPARISON

    private String prompt;
    private Map<String, Object> parameters;
    private String context;
}
