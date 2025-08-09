package com.smartshopai.businessintelligence.application.service;

import com.smartshopai.businessintelligence.application.dto.request.TrackEventRequest;
import com.smartshopai.businessintelligence.application.dto.response.AnalyticsEventResponse;
import com.smartshopai.businessintelligence.application.dto.response.BusinessMetricResponse;
import com.smartshopai.businessintelligence.application.mapper.AnalyticsMapper;
import com.smartshopai.businessintelligence.domain.entity.AnalyticsEvent;
import com.smartshopai.businessintelligence.domain.entity.BusinessMetric;
import com.smartshopai.businessintelligence.domain.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Application service for analytics operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsApplicationService {

    private final AnalyticsService analyticsService;
    private final AnalyticsMapper analyticsMapper;

    public AnalyticsEventResponse trackEvent(TrackEventRequest request) {
        log.info("Tracking analytics event - type: {}, user: {}", request.getEventType(), request.getUserId());
        
        AnalyticsEvent event = analyticsMapper.toEntity(request);
        AnalyticsEvent savedEvent = analyticsService.trackEvent(event);
        
        return analyticsMapper.toResponse(savedEvent);
    }

    public List<AnalyticsEventResponse> getEventsByType(String eventType, LocalDateTime start, LocalDateTime end) {
        log.debug("Getting events by type: {} from {} to {}", eventType, start, end);
        
        List<AnalyticsEvent> events = analyticsService.getEventsByType(eventType, start, end);
        return analyticsMapper.toEventResponseList(events);
    }

    public List<AnalyticsEventResponse> getEventsByUser(String userId, LocalDateTime start, LocalDateTime end) {
        log.debug("Getting events for user: {} from {} to {}", userId, start, end);
        
        List<AnalyticsEvent> events = analyticsService.getEventsByUser(userId, start, end);
        return analyticsMapper.toEventResponseList(events);
    }

    public List<AnalyticsEventResponse> getEventsByProduct(String productId, LocalDateTime start, LocalDateTime end) {
        log.debug("Getting events for product: {} from {} to {}", productId, start, end);
        
        List<AnalyticsEvent> events = analyticsService.getEventsByProduct(productId, start, end);
        return analyticsMapper.toEventResponseList(events);
    }

    public BusinessMetricResponse calculateMetric(String metricName, Map<String, Object> parameters) {
        log.info("Calculating metric: {}", metricName);
        
        BusinessMetric metric = analyticsService.calculateMetric(metricName, parameters);
        return analyticsMapper.toResponse(metric);
    }

    public List<BusinessMetricResponse> getMetricsByName(String metricName, LocalDateTime start, LocalDateTime end) {
        log.debug("Getting metrics by name: {} from {} to {}", metricName, start, end);
        
        List<BusinessMetric> metrics = analyticsService.getMetricsByName(metricName, start, end);
        return analyticsMapper.toMetricResponseList(metrics);
    }

    public List<BusinessMetricResponse> getMetricsByCategory(String category, LocalDateTime start, LocalDateTime end) {
        log.debug("Getting metrics by category: {} from {} to {}", category, start, end);
        
        List<BusinessMetric> metrics = analyticsService.getMetricsByCategory(category, start, end);
        return analyticsMapper.toMetricResponseList(metrics);
    }

    public Map<String, Object> getUserEngagementMetrics(String userId, LocalDateTime start, LocalDateTime end) {
        log.debug("Getting user engagement metrics for user: {} from {} to {}", userId, start, end);
        
        return analyticsService.getUserEngagementMetrics(userId, start, end);
    }

    public Map<String, Object> getProductPerformanceMetrics(String productId, LocalDateTime start, LocalDateTime end) {
        log.debug("Getting product performance metrics for product: {} from {} to {}", productId, start, end);
        
        return analyticsService.getProductPerformanceMetrics(productId, start, end);
    }

    public Map<String, Object> getConversionFunnelMetrics(LocalDateTime start, LocalDateTime end) {
        log.debug("Getting conversion funnel metrics from {} to {}", start, end);
        
        return analyticsService.getConversionFunnelMetrics(start, end);
    }

    public Map<String, Object> getRevenueMetrics(LocalDateTime start, LocalDateTime end) {
        log.debug("Getting revenue metrics from {} to {}", start, end);
        
        return analyticsService.getRevenueMetrics(start, end);
    }

    public Map<String, Object> getSearchAnalytics(LocalDateTime start, LocalDateTime end) {
        log.debug("Getting search analytics from {} to {}", start, end);
        
        return analyticsService.getSearchAnalytics(start, end);
    }

    public Map<String, Object> getAIRecommendationEffectiveness(LocalDateTime start, LocalDateTime end) {
        log.debug("Getting AI recommendation effectiveness from {} to {}", start, end);
        
        return analyticsService.getAIRecommendationEffectiveness(start, end);
    }

    public Map<String, Object> getRealTimeDashboardData() {
        log.debug("Getting real-time dashboard data");
        
        return analyticsService.getRealTimeDashboardData();
    }

    public byte[] exportAnalyticsData(String format, Map<String, Object> filters) {
        log.info("Exporting analytics data in format: {}", format);
        
        return analyticsService.exportAnalyticsData(format, filters);
    }
}
