package com.smartshopai.notification.domain.repository;

import com.smartshopai.notification.domain.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Notification entity
 */
@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    
    /**
     * Find notifications by user ID
     */
    List<Notification> findByUserId(String userId);
    
    /**
     * Find notifications by user ID and type
     */
    List<Notification> findByUserIdAndType(String userId, String type);
    
    /**
     * Find notifications by status
     */
    List<Notification> findByStatus(String status);
    
    /**
     * Find notifications by user ID and status
     */
    List<Notification> findByUserIdAndStatus(String userId, String status);
    
    /**
     * Find pending notifications
     */
    List<Notification> findByStatusAndScheduledAtBefore(String status, LocalDateTime before);
    
    /**
     * Find notifications by category
     */
    List<Notification> findByCategory(String category);
    
    /**
     * Find notifications by user ID and category
     */
    List<Notification> findByUserIdAndCategory(String userId, String category);
    
    /**
     * Find unread notifications by user ID
     */
    List<Notification> findByUserIdAndIsReadFalse(String userId);
    
    /**
     * Count unread notifications by user ID
     */
    long countByUserIdAndIsReadFalse(String userId);
}
