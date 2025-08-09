package com.smartshopai.notification.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Notification entity for SmartShopAI application
 * Represents various types of notifications
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    
    private String userId;
    private String type; // EMAIL, SMS, PUSH, IN_APP
    private String channel; // EMAIL, SMS, PUSH, WEBHOOK
    private String title;
    private String message;
    private String content;
    
    // Recipient information
    private String recipientEmail;
    private String recipientPhone;
    private String recipientDeviceToken;
    
    // Notification metadata
    private String category; // SYSTEM, PROMOTIONAL, TRANSACTIONAL, ALERT
    private String priority; // LOW, MEDIUM, HIGH, URGENT
    private Map<String, Object> metadata;
    
    // Delivery status
    private String status; // PENDING, SENT, DELIVERED, FAILED, READ
    private String deliveryStatus;
    private String errorMessage;
    private Integer retryCount;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime readAt;
    private LocalDateTime expiresAt;
    
    // Template information
    private String templateId;
    private String templateVersion;
    private Map<String, Object> templateData;
    
    // Tracking
    private boolean trackDelivery;
    private boolean trackOpen;
    private boolean trackClick;
    
    @Builder.Default
    private boolean isRead = false;
    
    // Setter for isRead field
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
