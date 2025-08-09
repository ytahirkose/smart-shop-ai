package com.smartshopai.airecommendation.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Behaviour metrics dto mirrored from user service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBehaviorMetricsDto {
    private Integer totalProductViews;
    private Integer totalSearches;
    private String shoppingPersonality;
    private java.util.List<String> recentViewedProductIds;
    private java.util.List<String> recentSearchQueries;
}
