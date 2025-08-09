package com.smartshopai.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.List;

/**
 * Lightweight response projection for UserBehaviorMetrics that can be safely exposed
 * to other bounded contexts / clients.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBehaviorMetricsResponse {
    private Integer totalSearches;
    private Integer totalProductViews;
    private Integer totalPurchases;
    private Double totalSpent;
    private String shoppingPersonality;

    /**
     * Optionally expose top-N keywords etc.
     */
    private Map<String, Integer> topSearchKeywords;
    private List<String> recentViewedProductIds;
}
