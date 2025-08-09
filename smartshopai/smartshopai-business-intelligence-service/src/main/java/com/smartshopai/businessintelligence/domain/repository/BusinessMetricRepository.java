package com.smartshopai.businessintelligence.domain.repository;

import com.smartshopai.businessintelligence.domain.entity.BusinessMetric;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for BusinessMetric entity
 */
@Repository
public interface BusinessMetricRepository extends MongoRepository<BusinessMetric, String> {

    List<BusinessMetric> findByMetricNameAndTimestampBetween(String metricName, LocalDateTime start, LocalDateTime end);
    
    List<BusinessMetric> findByCategoryAndTimestampBetween(String category, LocalDateTime start, LocalDateTime end);
    
    List<BusinessMetric> findByBusinessUnitAndTimestampBetween(String businessUnit, LocalDateTime start, LocalDateTime end);
    
    List<BusinessMetric> findByMetricTypeAndTimestampBetween(String metricType, LocalDateTime start, LocalDateTime end);
    
    List<BusinessMetric> findByStatusAndTimestampBetween(String status, LocalDateTime start, LocalDateTime end);
}
