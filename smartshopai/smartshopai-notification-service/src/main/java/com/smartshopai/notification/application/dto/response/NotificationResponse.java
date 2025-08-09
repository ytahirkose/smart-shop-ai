package com.smartshopai.notification.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Response DTO for notification data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private String id;
    private String userId;
    private String type;
    private String channel;
    private String title;
    private String message;
    private String content;

    // Recipient information
    private String recipientEmail;
    private String recipientPhone;
    private String recipientDeviceToken;

    // Notification metadata
    private String category;
    private String priority;
    private Map<String, Object> metadata;

    // Delivery status
    private String status;
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
    private boolean isRead;
}
