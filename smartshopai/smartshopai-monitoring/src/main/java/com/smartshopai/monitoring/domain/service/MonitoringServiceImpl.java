package com.smartshopai.monitoring.domain.service;

import com.smartshopai.monitoring.domain.entity.Metric;
import com.smartshopai.monitoring.domain.repository.MetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation of MonitoringService
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MonitoringServiceImpl implements MonitoringService {

    private final MetricRepository metricRepository;

    @Override
    public Metric recordMetric(String serviceName, String metricName, String metricType, Double value) {
        log.debug("Recording metric - service: {}, metric: {}, type: {}, value: {}", serviceName, metricName, metricType, value);
        
        Metric metric = Metric.builder()
                .id(UUID.randomUUID().toString())
                .serviceName(serviceName)
                .metricName(metricName)
                .metricType(metricType)
                .value(value)
                .timestamp(LocalDateTime.now())
                .status("ACTIVE")
                .enabled(true)
                .build();
        
        metric.prePersist();
        
        Metric savedMetric = metricRepository.save(metric);
        log.debug("Metric recorded successfully with ID: {}", savedMetric.getId());
        
        return savedMetric;
    }

    @Override
    public Metric recordMetric(String serviceName, String metricName, String metricType, Double value, Map<String, String> labels) {
        log.debug("Recording metric with labels - service: {}, metric: {}, type: {}, value: {}", serviceName, metricName, metricType, value);
        
        Metric metric = Metric.builder()
                .id(UUID.randomUUID().toString())
                .serviceName(serviceName)
                .metricName(metricName)
                .metricType(metricType)
                .value(value)
                .labels(labels)
                .timestamp(LocalDateTime.now())
                .status("ACTIVE")
                .enabled(true)
                .build();
        
        metric.prePersist();
        
        Metric savedMetric = metricRepository.save(metric);
        log.debug("Metric with labels recorded successfully with ID: {}", savedMetric.getId());
        
        return savedMetric;
    }

    @Override
    public List<Metric> getMetricsByServiceName(String serviceName) {
        log.debug("Getting metrics for service: {}", serviceName);
        return metricRepository.findByServiceName(serviceName);
    }

    @Override
    public List<Metric> getMetricsByMetricName(String metricName) {
        log.debug("Getting metrics by name: {}", metricName);
        return metricRepository.findByMetricName(metricName);
    }

    @Override
    public List<Metric> getMetricsByServiceNameAndMetricName(String serviceName, String metricName) {
        log.debug("Getting metrics for service: {} and metric: {}", serviceName, metricName);
        return metricRepository.findByServiceNameAndMetricName(serviceName, metricName);
    }

    @Override
    public List<Metric> getMetricsByTimestampRange(LocalDateTime start, LocalDateTime end) {
        log.debug("Getting metrics by timestamp range: {} to {}", start, end);
        return metricRepository.findByTimestampBetween(start, end);
    }

    @Override
    public List<Metric> getMetricsByServiceNameAndTimestampRange(String serviceName, LocalDateTime start, LocalDateTime end) {
        log.debug("Getting metrics for service: {} by timestamp range: {} to {}", serviceName, start, end);
        return metricRepository.findByServiceNameAndTimestampBetween(serviceName, start, end);
    }

    @Override
    public Map<String, Object> getSystemHealth() {
        log.debug("Getting system health status");
        
        Map<String, Object> health = new HashMap<>();
        health.put("status", "HEALTHY");
        health.put("timestamp", LocalDateTime.now());
        health.put("services", List.of("user-service", "product-service", "ai-analysis-service", "ai-recommendation-service", "ai-search-service", "notification-service"));
        health.put("database", "UP");
        health.put("cache", "UP");
        health.put("messageQueue", "UP");
        
        return health;
    }

    @Override
    public Map<String, Object> getServiceHealth(String serviceName) {
        log.debug("Getting health status for service: {}", serviceName);
        
        Map<String, Object> health = new HashMap<>();
        health.put("service", serviceName);
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("responseTime", 150.0);
        health.put("errorRate", 0.01);
        health.put("throughput", 1000.0);
        
        return health;
    }

    @Override
    public Map<String, Object> getPerformanceMetrics() {
        log.debug("Getting performance metrics");
        
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("averageResponseTime", 200.0);
        metrics.put("p95ResponseTime", 500.0);
        metrics.put("p99ResponseTime", 1000.0);
        metrics.put("errorRate", 0.02);
        metrics.put("throughput", 5000.0);
        metrics.put("activeConnections", 150);
        metrics.put("memoryUsage", 75.5);
        metrics.put("cpuUsage", 45.2);
        
        return metrics;
    }

    @Override
    public Map<String, Object> getBusinessMetrics() {
        log.debug("Getting business metrics");
        
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalUsers", 10000);
        metrics.put("activeUsers", 2500);
        metrics.put("totalProducts", 50000);
        metrics.put("totalAnalyses", 15000);
        metrics.put("totalRecommendations", 8000);
        metrics.put("totalSearches", 12000);
        metrics.put("conversionRate", 0.15);
        metrics.put("averageOrderValue", 150.0);
        metrics.put("customerSatisfaction", 4.5);
        
        return metrics;
    }

    @Override
    public Map<String, Object> getErrorMetrics(String serviceName) {
        log.debug("Getting error metrics for service: {}", serviceName);
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("service", serviceName);
        metrics.put("errorsLastHour", 12);
        metrics.put("errorsLastDay", 215);
        metrics.put("topError", "BusinessException");
        metrics.put("errorRate", 0.01);
        return metrics;
    }

    @Override
    public void alert(String serviceName, String alertType, String message, Map<String, Object> data) {
        log.warn("ALERT [{}] {} - {} | data={} ", serviceName, alertType, message, data);
    }

    @Override
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("systemHealth", getSystemHealth());
        dashboard.put("performance", getPerformanceMetrics());
        dashboard.put("business", getBusinessMetrics());
        return dashboard;
    }

    @Override
    public void cleanupOldMetrics(Integer daysToKeep) {
        LocalDateTime retentionDate = LocalDateTime.now().minusDays(daysToKeep);
        log.info("Cleaning up metrics older than {} days (before {})", daysToKeep, retentionDate);
        
        metricRepository.deleteByTimestampBefore(retentionDate);
        
        log.info("Metrics cleanup finished.");
    }
}
