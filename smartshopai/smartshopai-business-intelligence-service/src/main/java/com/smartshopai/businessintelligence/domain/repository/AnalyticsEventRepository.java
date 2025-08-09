package com.smartshopai.businessintelligence.domain.repository;

import com.smartshopai.businessintelligence.domain.entity.AnalyticsEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for AnalyticsEvent entity
 */
@Repository
public interface AnalyticsEventRepository extends MongoRepository<AnalyticsEvent, String> {

    List<AnalyticsEvent> findByEventTypeAndTimestampBetween(String eventType, LocalDateTime start, LocalDateTime end);
    
    List<AnalyticsEvent> findByUserIdAndTimestampBetween(String userId, LocalDateTime start, LocalDateTime end);
    
    List<AnalyticsEvent> findByProductIdAndTimestampBetween(String productId, LocalDateTime start, LocalDateTime end);
    
    List<AnalyticsEvent> findByProductIdAndEventTypeAndTimestampBetween(String productId, String eventType, LocalDateTime start, LocalDateTime end);
    
    @Query("{'userId': ?0, 'timestamp': {$gte: ?1, $lte: ?2}}")
    Long countByUserIdAndTimestampBetween(String userId, LocalDateTime start, LocalDateTime end);
    
    @Query("{'userId': ?0, 'timestamp': {$gte: ?1, $lte: ?2}}")
    Long countDistinctSessionIdByUserIdAndTimestampBetween(String userId, LocalDateTime start, LocalDateTime end);
    
    @Query("{'productId': ?0, 'eventType': ?1, 'timestamp': {$gte: ?2, $lte: ?3}}")
    Long countByProductIdAndEventTypeAndTimestampBetween(String productId, String eventType, LocalDateTime start, LocalDateTime end);
}
