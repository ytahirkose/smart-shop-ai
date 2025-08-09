package com.smartshopai.monitoring.domain.service;

import com.smartshopai.monitoring.domain.entity.Metric;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Domain service for monitoring operations
 */
public interface MonitoringService {

    /**
     * Record a metric
     */
    Metric recordMetric(String serviceName, String metricName, String metricType, Double value);

    /**
     * Record a metric with labels
     */
    Metric recordMetric(String serviceName, String metricName, String metricType, Double value, Map<String, String> labels);

    /**
     * Get metrics by service name
     */
    List<Metric> getMetricsByServiceName(String serviceName);

    /**
     * Get metrics by metric name
     */
    List<Metric> getMetricsByMetricName(String metricName);

    /**
     * Get metrics by service name and metric name
     */
    List<Metric> getMetricsByServiceNameAndMetricName(String serviceName, String metricName);

    /**
     * Get metrics by timestamp range
     */
    List<Metric> getMetricsByTimestampRange(LocalDateTime start, LocalDateTime end);

    /**
     * Get metrics by service name and timestamp range
     */
    List<Metric> getMetricsByServiceNameAndTimestampRange(String serviceName, LocalDateTime start, LocalDateTime end);

    /**
     * Get system health status
     */
    Map<String, Object> getSystemHealth();

    /**
     * Get service health status
     */
    Map<String, Object> getServiceHealth(String serviceName);

    /**
     * Get performance metrics
     */
    Map<String, Object> getPerformanceMetrics();

    /**
     * Get business metrics
     */
    Map<String, Object> getBusinessMetrics();

    Map<String, Object> getErrorMetrics(String serviceName);

    void alert(String serviceName, String alertType, String message, Map<String, Object> data);

    Map<String, Object> getDashboardData();

    void cleanupOldMetrics(Integer daysToKeep);
}
