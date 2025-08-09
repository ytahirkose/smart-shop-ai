package com.smartshopai.product.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Request DTO for product comparison
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComparisonRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotEmpty(message = "Product IDs are required")
    @Size(min = 2, max = 10, message = "Must compare between 2 and 10 products")
    private List<String> productIds;

    private String comparisonType; // FEATURE, PRICE, QUALITY, OVERALL
    private Map<String, Object> preferences;
    private String context;
}
