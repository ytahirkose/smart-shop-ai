package com.smartshopai.notification.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

/**
 * Request DTO for sending notifications
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Notification type is required")
    private String type; // EMAIL, SMS, PUSH, IN_APP

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Message is required")
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

    // Template information
    private String templateId;
    private String templateVersion;
    private Map<String, Object> templateData;

    // Scheduling
    private String scheduledAt;

    // Tracking
    private boolean trackDelivery;
    private boolean trackOpen;
    private boolean trackClick;
}
