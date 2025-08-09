package com.smartshopai.businessintelligence.presentation.controller;

import com.smartshopai.common.dto.BaseResponse;
import com.smartshopai.businessintelligence.application.dto.request.TrackEventRequest;
import com.smartshopai.businessintelligence.application.dto.response.AnalyticsEventResponse;
import com.smartshopai.businessintelligence.application.dto.response.BusinessMetricResponse;
import com.smartshopai.businessintelligence.application.service.AnalyticsApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * REST controller for analytics operations
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Analytics and business intelligence operations")
public class AnalyticsController {

    private final AnalyticsApplicationService analyticsApplicationService;

    @PostMapping("/events/track")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Track analytics event", description = "Tracks a new analytics event")
    public ResponseEntity<BaseResponse<AnalyticsEventResponse>> trackEvent(@Valid @RequestBody TrackEventRequest request) {
        log.info("Tracking analytics event - type: {}, user: {}", request.getEventType(), request.getUserId());
        
        AnalyticsEventResponse response = analyticsApplicationService.trackEvent(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(response, "Event tracked successfully"));
    }

    @GetMapping("/events/type/{eventType}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get events by type", description = "Retrieves analytics events by type")
    public ResponseEntity<BaseResponse<List<AnalyticsEventResponse>>> getEventsByType(
            @PathVariable String eventType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting events by type: {} from {} to {}", eventType, start, end);
        
        List<AnalyticsEventResponse> responses = analyticsApplicationService.getEventsByType(eventType, start, end);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/events/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get events by user", description = "Retrieves analytics events for a specific user")
    public ResponseEntity<BaseResponse<List<AnalyticsEventResponse>>> getEventsByUser(
            @PathVariable String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting events for user: {} from {} to {}", userId, start, end);
        
        List<AnalyticsEventResponse> responses = analyticsApplicationService.getEventsByUser(userId, start, end);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/events/product/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get events by product", description = "Retrieves analytics events for a specific product")
    public ResponseEntity<BaseResponse<List<AnalyticsEventResponse>>> getEventsByProduct(
            @PathVariable String productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting events for product: {} from {} to {}", productId, start, end);
        
        List<AnalyticsEventResponse> responses = analyticsApplicationService.getEventsByProduct(productId, start, end);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @PostMapping("/metrics/calculate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Calculate metric", description = "Calculates a business metric")
    public ResponseEntity<BaseResponse<BusinessMetricResponse>> calculateMetric(
            @RequestParam String metricName,
            @RequestBody Map<String, Object> parameters) {
        log.info("Calculating metric: {}", metricName);
        
        BusinessMetricResponse response = analyticsApplicationService.calculateMetric(metricName, parameters);
        
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/metrics/{metricName}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get metrics by name", description = "Retrieves metrics by name")
    public ResponseEntity<BaseResponse<List<BusinessMetricResponse>>> getMetricsByName(
            @PathVariable String metricName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting metrics by name: {} from {} to {}", metricName, start, end);
        
        List<BusinessMetricResponse> responses = analyticsApplicationService.getMetricsByName(metricName, start, end);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/metrics/category/{category}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get metrics by category", description = "Retrieves metrics by category")
    public ResponseEntity<BaseResponse<List<BusinessMetricResponse>>> getMetricsByCategory(
            @PathVariable String category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting metrics by category: {} from {} to {}", category, start, end);
        
        List<BusinessMetricResponse> responses = analyticsApplicationService.getMetricsByCategory(category, start, end);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/user-engagement/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get user engagement metrics", description = "Retrieves user engagement metrics")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getUserEngagementMetrics(
            @PathVariable String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting user engagement metrics for user: {} from {} to {}", userId, start, end);
        
        Map<String, Object> metrics = analyticsApplicationService.getUserEngagementMetrics(userId, start, end);
        
        return ResponseEntity.ok(BaseResponse.success(metrics));
    }

    @GetMapping("/product-performance/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get product performance metrics", description = "Retrieves product performance metrics")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getProductPerformanceMetrics(
            @PathVariable String productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting product performance metrics for product: {} from {} to {}", productId, start, end);
        
        Map<String, Object> metrics = analyticsApplicationService.getProductPerformanceMetrics(productId, start, end);
        
        return ResponseEntity.ok(BaseResponse.success(metrics));
    }

    @GetMapping("/conversion-funnel")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get conversion funnel metrics", description = "Retrieves conversion funnel metrics")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getConversionFunnelMetrics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting conversion funnel metrics from {} to {}", start, end);
        
        Map<String, Object> metrics = analyticsApplicationService.getConversionFunnelMetrics(start, end);
        
        return ResponseEntity.ok(BaseResponse.success(metrics));
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get revenue metrics", description = "Retrieves revenue metrics")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getRevenueMetrics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting revenue metrics from {} to {}", start, end);
        
        Map<String, Object> metrics = analyticsApplicationService.getRevenueMetrics(start, end);
        
        return ResponseEntity.ok(BaseResponse.success(metrics));
    }

    @GetMapping("/search-analytics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get search analytics", description = "Retrieves search analytics")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getSearchAnalytics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting search analytics from {} to {}", start, end);
        
        Map<String, Object> analytics = analyticsApplicationService.getSearchAnalytics(start, end);
        
        return ResponseEntity.ok(BaseResponse.success(analytics));
    }

    @GetMapping("/ai-recommendation-effectiveness")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get AI recommendation effectiveness", description = "Retrieves AI recommendation effectiveness metrics")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getAIRecommendationEffectiveness(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting AI recommendation effectiveness from {} to {}", start, end);
        
        Map<String, Object> metrics = analyticsApplicationService.getAIRecommendationEffectiveness(start, end);
        
        return ResponseEntity.ok(BaseResponse.success(metrics));
    }

    @GetMapping("/dashboard/real-time")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get real-time dashboard data", description = "Retrieves real-time dashboard data")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getRealTimeDashboardData() {
        log.debug("Getting real-time dashboard data");
        
        Map<String, Object> data = analyticsApplicationService.getRealTimeDashboardData();
        
        return ResponseEntity.ok(BaseResponse.success(data));
    }

    @PostMapping("/export")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Export analytics data", description = "Exports analytics data in specified format")
    public ResponseEntity<byte[]> exportAnalyticsData(
            @RequestParam String format,
            @RequestBody Map<String, Object> filters) {
        log.info("Exporting analytics data in format: {}", format);
        
        byte[] data = analyticsApplicationService.exportAnalyticsData(format, filters);
        
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=analytics_export." + format.toLowerCase())
                .body(data);
    }

    @GetMapping("/health")
    @Operation(summary = "Analytics service health check", description = "Checks if analytics service is healthy")
    public ResponseEntity<BaseResponse<String>> healthCheck() {
        log.debug("Health check requested");
        
        return ResponseEntity.ok(BaseResponse.success("Analytics Service is healthy"));
    }
}
