package com.smartshopai.monitoring.application.service;

import com.smartshopai.monitoring.application.dto.request.GetMetricsRequest;
import com.smartshopai.monitoring.application.dto.request.HealthCheckRequest;
import com.smartshopai.monitoring.application.dto.request.RecordMetricRequest;
import com.smartshopai.monitoring.application.dto.response.MetricResponse;
import com.smartshopai.monitoring.application.mapper.MonitoringMapper;
import com.smartshopai.monitoring.domain.entity.Metric;
import com.smartshopai.monitoring.domain.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Application service for monitoring operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MonitoringApplicationService {

    private final MonitoringService monitoringService;
    private final MonitoringMapper monitoringMapper;

    public MetricResponse recordMetric(RecordMetricRequest request) {
        log.info("Recording metric: {} for service: {}", request.getMetricName(), request.getServiceName());

        Metric metric;
        if (request.getLabels() != null && !request.getLabels().isEmpty()) {
            metric = monitoringService.recordMetric(
                    request.getServiceName(),
                    request.getMetricName(),
                    request.getMetricType(),
                    request.getValue(),
                    request.getLabels()
            );
        } else {
            metric = monitoringService.recordMetric(
                    request.getServiceName(),
                    request.getMetricName(),
                    request.getMetricType(),
                    request.getValue()
            );
        }

        return monitoringMapper.toResponse(metric);
    }

    public List<MetricResponse> getMetrics(GetMetricsRequest request) {
        log.debug("Getting metrics: {} for service: {}", request.getMetricName(), request.getServiceName());

        List<Metric> metrics;
        var hasService = request.getServiceName() != null && !request.getServiceName().isBlank();
        var hasMetric = request.getMetricName() != null && !request.getMetricName().isBlank();
        var hasRange = request.getStartTime() != null && request.getEndTime() != null;

        if (hasService && hasRange) {
            metrics = monitoringService.getMetricsByServiceNameAndTimestampRange(
                    request.getServiceName(), request.getStartTime(), request.getEndTime());
        } else if (hasService && hasMetric) {
            metrics = monitoringService.getMetricsByServiceNameAndMetricName(
                    request.getServiceName(), request.getMetricName());
        } else if (hasService) {
            metrics = monitoringService.getMetricsByServiceName(request.getServiceName());
        } else if (hasMetric) {
            metrics = monitoringService.getMetricsByMetricName(request.getMetricName());
        } else if (hasRange) {
            metrics = monitoringService.getMetricsByTimestampRange(request.getStartTime(), request.getEndTime());
        } else {
            metrics = List.of();
        }

        return monitoringMapper.toResponseList(metrics);
    }

    public Map<String, Object> getSystemHealth() {
        log.debug("Getting system health");
        return monitoringService.getSystemHealth();
    }

    public Map<String, Object> getServiceHealth(HealthCheckRequest request) {
        log.debug("Getting health for service: {}", request.getServiceName());

        return monitoringService.getServiceHealth(request.getServiceName());
    }

    public Map<String, Object> getPerformanceMetrics(String serviceName) {
        log.debug("Getting performance metrics for service: {}", serviceName);
        return monitoringService.getPerformanceMetrics();
    }

    public Map<String, Object> getBusinessMetrics(String serviceName) {
        log.debug("Getting business metrics for service: {}", serviceName);
        return monitoringService.getBusinessMetrics();
    }

    public Map<String, Object> getErrorMetrics(String serviceName) {
        log.debug("Getting error metrics for service: {}", serviceName);
        return monitoringService.getErrorMetrics(serviceName);
    }

    public void alert(String serviceName, String alertType, String message, Map<String, Object> data) {
        log.warn("Alert triggered - service: {}, type: {}, message: {}", serviceName, alertType, message);
        monitoringService.alert(serviceName, alertType, message, data);
    }

    public Map<String, Object> getDashboardData() {
        log.debug("Getting dashboard data");
        return monitoringService.getDashboardData();
    }

    public void cleanupOldMetrics(Integer daysToKeep) {
        log.info("Cleaning up metrics older than {} days", daysToKeep);
        monitoringService.cleanupOldMetrics(daysToKeep);
    }
}
