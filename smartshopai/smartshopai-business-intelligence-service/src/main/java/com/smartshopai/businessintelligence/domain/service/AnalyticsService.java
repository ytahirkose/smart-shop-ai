package com.smartshopai.businessintelligence.domain.service;

import com.smartshopai.businessintelligence.domain.entity.AnalyticsEvent;
import com.smartshopai.businessintelligence.domain.entity.BusinessMetric;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Domain service for analytics operations
 */
public interface AnalyticsService {

    /**
     * Track an analytics event
     */
    AnalyticsEvent trackEvent(AnalyticsEvent event);

    /**
     * Get events by type
     */
    List<AnalyticsEvent> getEventsByType(String eventType, LocalDateTime start, LocalDateTime end);

    /**
     * Get events by user
     */
    List<AnalyticsEvent> getEventsByUser(String userId, LocalDateTime start, LocalDateTime end);

    /**
     * Get events by product
     */
    List<AnalyticsEvent> getEventsByProduct(String productId, LocalDateTime start, LocalDateTime end);

    /**
     * Calculate business metrics
     */
    BusinessMetric calculateMetric(String metricName, Map<String, Object> parameters);

    /**
     * Get metrics by name
     */
    List<BusinessMetric> getMetricsByName(String metricName, LocalDateTime start, LocalDateTime end);

    /**
     * Get metrics by category
     */
    List<BusinessMetric> getMetricsByCategory(String category, LocalDateTime start, LocalDateTime end);

    /**
     * Get user engagement metrics
     */
    Map<String, Object> getUserEngagementMetrics(String userId, LocalDateTime start, LocalDateTime end);

    /**
     * Get product performance metrics
     */
    Map<String, Object> getProductPerformanceMetrics(String productId, LocalDateTime start, LocalDateTime end);

    /**
     * Get conversion funnel metrics
     */
    Map<String, Object> getConversionFunnelMetrics(LocalDateTime start, LocalDateTime end);

    /**
     * Get revenue metrics
     */
    Map<String, Object> getRevenueMetrics(LocalDateTime start, LocalDateTime end);

    /**
     * Get search analytics
     */
    Map<String, Object> getSearchAnalytics(LocalDateTime start, LocalDateTime end);

    /**
     * Get AI recommendation effectiveness
     */
    Map<String, Object> getAIRecommendationEffectiveness(LocalDateTime start, LocalDateTime end);

    /**
     * Generate real-time dashboard data
     */
    Map<String, Object> getRealTimeDashboardData();

    /**
     * Export analytics data
     */
    byte[] exportAnalyticsData(String format, Map<String, Object> filters);
}
