package com.smartshopai.notification.application.service;

import com.smartshopai.notification.application.dto.request.SendEmailRequest;
import com.smartshopai.notification.application.dto.request.SendNotificationRequest;
import com.smartshopai.notification.application.dto.request.SendPushRequest;
import com.smartshopai.notification.application.dto.request.SendSmsRequest;
import com.smartshopai.notification.application.dto.response.NotificationResponse;
import com.smartshopai.notification.application.mapper.NotificationMapper;
import com.smartshopai.notification.domain.entity.Notification;
import com.smartshopai.notification.domain.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service for notification operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationApplicationService {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    public NotificationResponse sendNotification(SendNotificationRequest request) {
        log.info("Sending notification to user: {}", request.getUserId());
        
        Notification notification;
        switch (request.getType()) {
            case "EMAIL" -> notification = notificationService.sendEmailNotification(
                    request.getUserId(),
                    request.getRecipientEmail(),
                    request.getTitle(),
                    request.getContent() != null ? request.getContent() : request.getMessage()
            );
            case "SMS" -> notification = notificationService.sendSmsNotification(
                    request.getUserId(),
                    request.getRecipientPhone(),
                    request.getMessage()
            );
            case "PUSH" -> notification = notificationService.sendPushNotification(
                    request.getUserId(),
                    request.getRecipientDeviceToken(),
                    request.getTitle(),
                    request.getMessage()
            );
            default -> notification = notificationService.sendInAppNotification(
                    request.getUserId(),
                    request.getTitle(),
                    request.getMessage()
            );
        }
        
        return notificationMapper.toResponse(notification);
    }

    public NotificationResponse sendEmail(SendEmailRequest request) {
        log.info("Sending email to: {}", request.getTo());
        
        Notification notification = notificationService.sendEmailNotification(
                null,
                request.getTo(),
                request.getSubject(),
                request.getContent()
        );
        
        return notificationMapper.toResponse(notification);
    }

    public NotificationResponse sendSms(SendSmsRequest request) {
        log.info("Sending SMS to: {}", request.getTo());
        
        Notification notification = notificationService.sendSmsNotification(
                null,
                request.getTo(),
                request.getMessage()
        );
        
        return notificationMapper.toResponse(notification);
    }

    public NotificationResponse sendPushNotification(SendPushRequest request) {
        log.info("Sending push notification to user: {}", request.getUserId());
        
        Notification notification = notificationService.sendPushNotification(
                request.getUserId(),
                request.getDeviceToken(),
                request.getTitle(),
                request.getBody()
        );
        
        return notificationMapper.toResponse(notification);
    }

    public List<NotificationResponse> getUserNotifications(String userId) {
        log.debug("Getting notifications for user: {}", userId);
        
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return notificationMapper.toResponseList(notifications);
    }

    public List<NotificationResponse> getUnreadNotifications(String userId) {
        log.debug("Getting unread notifications for user: {}", userId);
        
        List<Notification> notifications = notificationService.getUnreadNotificationsByUserId(userId);
        return notificationMapper.toResponseList(notifications);
    }

    public void markAsRead(String notificationId) {
        log.debug("Marking notification as read: {}", notificationId);
        notificationService.markAsRead(notificationId);
    }

    public void markAllAsRead(String userId) {
        log.debug("Marking all notifications as read for user: {}", userId);
        notificationService.markAllAsRead(userId);
    }

    public void deleteNotification(String notificationId) {
        log.info("Deleting notification: {}", notificationId);
        notificationService.deleteNotification(notificationId);
    }

    public void deleteAllNotifications(String userId) {
        log.info("Deleting all notifications for user: {}", userId);
        // Not implemented in domain service; optional: add repository deleteByUserId if needed
    }

    public NotificationResponse getNotificationById(String notificationId) {
        log.debug("Getting notification by ID: {}", notificationId);
        
        Notification notification = notificationService.getNotificationById(notificationId);
        return notificationMapper.toResponse(notification);
    }
}
