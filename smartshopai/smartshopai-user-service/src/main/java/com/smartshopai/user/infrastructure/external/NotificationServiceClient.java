package com.smartshopai.user.infrastructure.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Client for communicating with Notification Service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationServiceClient {

    private final RestTemplate restTemplate;
    private static final String NOTIFICATION_SERVICE_URL = "http://smartshopai-notification-service";

    /**
     * Send email notification
     */
    public void sendEmailNotification(String userId, String email, String subject, String content) {
        log.debug("Sending email notification to user: {}, email: {}", userId, email);
        
        try {
            Map<String, Object> request = Map.of(
                "userId", userId,
                "email", email,
                "subject", subject,
                "content", content,
                "type", "EMAIL"
            );
            
            restTemplate.postForObject(
                NOTIFICATION_SERVICE_URL + "/api/v1/notifications/email",
                request,
                Void.class
            );
            
            log.debug("Email notification sent successfully to user: {}", userId);
        } catch (Exception e) {
            log.error("Failed to send email notification to user: {}", userId, e);
        }
    }

    /**
     * Send push notification
     */
    public void sendPushNotification(String userId, String title, String message) {
        log.debug("Sending push notification to user: {}", userId);
        
        try {
            Map<String, Object> request = Map.of(
                "userId", userId,
                "title", title,
                "message", message,
                "type", "PUSH"
            );
            
            restTemplate.postForObject(
                NOTIFICATION_SERVICE_URL + "/api/v1/notifications/push",
                request,
                Void.class
            );
            
            log.debug("Push notification sent successfully to user: {}", userId);
        } catch (Exception e) {
            log.error("Failed to send push notification to user: {}", userId, e);
        }
    }

    /**
     * Send SMS notification
     */
    public void sendSmsNotification(String userId, String phoneNumber, String message) {
        log.debug("Sending SMS notification to user: {}", userId);
        
        try {
            Map<String, Object> request = Map.of(
                "userId", userId,
                "phoneNumber", phoneNumber,
                "message", message,
                "type", "SMS"
            );
            
            restTemplate.postForObject(
                NOTIFICATION_SERVICE_URL + "/api/v1/notifications/sms",
                request,
                Void.class
            );
            
            log.debug("SMS notification sent successfully to user: {}", userId);
        } catch (Exception e) {
            log.error("Failed to send SMS notification to user: {}", userId, e);
        }
    }
}
