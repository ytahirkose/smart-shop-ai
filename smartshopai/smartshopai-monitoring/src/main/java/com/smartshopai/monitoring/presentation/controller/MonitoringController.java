package com.smartshopai.monitoring.presentation.controller;

import com.smartshopai.monitoring.application.dto.request.GetMetricsRequest;
import com.smartshopai.monitoring.application.dto.request.HealthCheckRequest;
import com.smartshopai.monitoring.application.dto.request.RecordMetricRequest;
import com.smartshopai.monitoring.application.dto.response.MetricResponse;
import com.smartshopai.monitoring.application.service.MonitoringApplicationService;
import com.smartshopai.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST controller for monitoring operations
 */
@Slf4j
@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final MonitoringApplicationService monitoringApplicationService;

    @PostMapping("/metrics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<MetricResponse>> recordMetric(
            @Valid @RequestBody RecordMetricRequest request) {
        log.info("Recording metric: {} for service: {}", request.getMetricName(), request.getServiceName());
        
        MetricResponse response = monitoringApplicationService.recordMetric(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/metrics/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<List<MetricResponse>>> getMetrics(
            @Valid @RequestBody GetMetricsRequest request) {
        log.debug("Getting metrics: {} for service: {}", request.getMetricName(), request.getServiceName());
        
        List<MetricResponse> response = monitoringApplicationService.getMetrics(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/health/system")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getSystemHealth() {
        log.debug("Getting system health");
        
        Map<String, Object> response = monitoringApplicationService.getSystemHealth();
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/health/service")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getServiceHealth(
            @Valid @RequestBody HealthCheckRequest request) {
        log.debug("Getting health for service: {}", request.getServiceName());
        
        Map<String, Object> response = monitoringApplicationService.getServiceHealth(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/performance/{serviceName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getPerformanceMetrics(
            @PathVariable String serviceName) {
        log.debug("Getting performance metrics for service: {}", serviceName);
        
        Map<String, Object> response = monitoringApplicationService.getPerformanceMetrics(serviceName);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/business/{serviceName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getBusinessMetrics(
            @PathVariable String serviceName) {
        log.debug("Getting business metrics for service: {}", serviceName);
        
        Map<String, Object> response = monitoringApplicationService.getBusinessMetrics(serviceName);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/errors/{serviceName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getErrorMetrics(
            @PathVariable String serviceName) {
        log.debug("Getting error metrics for service: {}", serviceName);
        
        Map<String, Object> response = monitoringApplicationService.getErrorMetrics(serviceName);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/alert")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> alert(
            @RequestParam String serviceName, @RequestParam String alertType, 
            @RequestParam String message, @RequestBody(required = false) Map<String, Object> data) {
        log.warn("Alert triggered - service: {}, type: {}, message: {}", serviceName, alertType, message);
        
        monitoringApplicationService.alert(serviceName, alertType, message, data);
        return ResponseEntity.ok(BaseResponse.success("Alert sent successfully"));
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getDashboardData() {
        log.debug("Getting dashboard data");
        
        Map<String, Object> response = monitoringApplicationService.getDashboardData();
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/cleanup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> cleanupOldMetrics(@RequestParam Integer daysToKeep) {
        log.info("Cleaning up metrics older than {} days", daysToKeep);
        
        monitoringApplicationService.cleanupOldMetrics(daysToKeep);
        return ResponseEntity.ok(BaseResponse.success("Old metrics cleaned up successfully"));
    }
}
