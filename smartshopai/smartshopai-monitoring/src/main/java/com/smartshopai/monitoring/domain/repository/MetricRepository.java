package com.smartshopai.monitoring.domain.repository;

import com.smartshopai.monitoring.domain.entity.Metric;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Metric entity
 */
@Repository
public interface MetricRepository extends MongoRepository<Metric, String> {
    
    /**
     * Find metrics by service name
     */
    List<Metric> findByServiceName(String serviceName);
    
    /**
     * Find metrics by metric name
     */
    List<Metric> findByMetricName(String metricName);
    
    /**
     * Find metrics by service name and metric name
     */
    List<Metric> findByServiceNameAndMetricName(String serviceName, String metricName);
    
    /**
     * Find metrics by metric type
     */
    List<Metric> findByMetricType(String metricType);
    
    /**
     * Find metrics by status
     */
    List<Metric> findByStatus(String status);
    
    /**
     * Find enabled metrics
     */
    List<Metric> findByEnabledTrue();
    
    /**
     * Find metrics by timestamp range
     */
    List<Metric> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * Find metrics by service name and timestamp range
     */
    List<Metric> findByServiceNameAndTimestampBetween(String serviceName, LocalDateTime start, LocalDateTime end);
    
    /**
     * Delete metrics older than a given timestamp
     */
    void deleteByTimestampBefore(LocalDateTime timestamp);
}
