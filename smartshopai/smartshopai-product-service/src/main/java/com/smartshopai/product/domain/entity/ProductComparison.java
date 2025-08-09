package com.smartshopai.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Product comparison data for AI analysis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComparison {

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
