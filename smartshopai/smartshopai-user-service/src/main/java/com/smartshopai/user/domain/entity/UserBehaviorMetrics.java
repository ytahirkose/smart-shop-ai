package com.smartshopai.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User behavior metrics entity for tracking user behavior patterns
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBehaviorMetrics {
    
    private String userId;
    
    // Search behavior
    private Integer totalSearches;
    private Map<String, Integer> searchCategories;
    private Map<String, Integer> searchKeywords;
    private List<String> recentSearchQueries;
    
    // Product interaction behavior
    private Integer totalProductViews;
    private Map<String, Integer> viewedProducts;
    private List<String> recentViewedProductIds;
    // Personality insight derived from shopping behaviour
    private String shoppingPersonality;
    private Map<String, Integer> comparedProducts;
    private Map<String, Integer> wishlistedProducts;
    
    // Purchase behavior
    private Integer totalPurchases;
    private Double totalSpent;
    private Map<String, Integer> purchasedCategories;
    private Map<String, Integer> purchasedBrands;
    
    // Session behavior
    private Double averageSessionDuration;
    private Integer totalSessions;
    private LocalDateTime lastSessionStart;
    private LocalDateTime lastSessionEnd;
    
    // AI analysis
    private String aiInsights;
    private Boolean aiAnalysisCompleted;
    private LocalDateTime aiAnalysisDate;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    
    /**
     * Initialize default values
     */
    public void initializeDefaults() {
        if (totalSearches == null) totalSearches = 0;
        if (searchCategories == null) searchCategories = new HashMap<>();
        if (searchKeywords == null) searchKeywords = new HashMap<>();
        if (recentSearchQueries == null) recentSearchQueries = new ArrayList<>();
        if (totalProductViews == null) totalProductViews = 0;
        if (viewedProducts == null) viewedProducts = new HashMap<>();
        if (recentViewedProductIds == null) recentViewedProductIds = new ArrayList<>();
        if (comparedProducts == null) comparedProducts = new HashMap<>();
        if (wishlistedProducts == null) wishlistedProducts = new HashMap<>();
        if (totalPurchases == null) totalPurchases = 0;
        if (totalSpent == null) totalSpent = 0.0;
        if (purchasedCategories == null) purchasedCategories = new HashMap<>();
        if (purchasedBrands == null) purchasedBrands = new HashMap<>();
        if (averageSessionDuration == null) averageSessionDuration = 0.0;
        if (totalSessions == null) totalSessions = 0;
        if (aiAnalysisCompleted == null) aiAnalysisCompleted = false;
    }
}
