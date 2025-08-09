package com.smartshopai.notification.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Request DTO for sending push notifications
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendPushRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Body is required")
    private String body;

    private String deviceToken;
    private String topic;
    private Map<String, Object> data;
    private String imageUrl;
    private String actionUrl;
    private String priority; // HIGH, NORMAL, LOW
}
