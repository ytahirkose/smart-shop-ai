package com.smartshopai.product.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response DTO for product comparison data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComparisonResponse {

    private String comparedProductId;
    private String comparedProductName;
    private String comparedProductBrand;
    private BigDecimal comparedProductPrice;

    // Comparison metrics
    private Double qualityComparison;
    private Double valueComparison;
    private Double featureComparison;
    private Double priceComparison;

    // Detailed comparison
    private String qualityDifference;
    private String valueDifference;
    private String featureDifference;
    private String priceDifference;

    // Recommendation
    private String recommendation;
    private String reason;
    private boolean isBetterAlternative;
}
