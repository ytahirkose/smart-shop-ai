package com.smartshopai.notification.domain.service;

import com.smartshopai.notification.domain.entity.Notification;
import com.smartshopai.notification.domain.repository.NotificationRepository;
import com.smartshopai.notification.infrastructure.service.EmailService;
import com.smartshopai.notification.infrastructure.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of NotificationService
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final SmsService smsService;

    @Override
    public Notification sendEmailNotification(String userId, String email, String subject, String content) {
        log.info("Sending email notification to user: {}, email: {}", userId, email);
        
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .type("EMAIL")
                .channel("EMAIL")
                .title(subject)
                .message(subject)
                .content(content)
                .recipientEmail(email)
                .category("TRANSACTIONAL")
                .priority("MEDIUM")
                .status("PENDING")
                .retryCount(0)
                .trackDelivery(true)
                .build();
        
        notification.prePersist();
        
        Notification savedNotification = notificationRepository.save(notification);
        log.info("Email notification created with ID: {}", savedNotification.getId());
        
        // Send actual email
        try {
            emailService.sendEmail(email, subject, content);
            savedNotification.setStatus("SENT");
            savedNotification.setSentAt(LocalDateTime.now());
            notificationRepository.save(savedNotification);
        } catch (Exception e) {
            log.error("Failed to send email, notification ID: {}", savedNotification.getId(), e);
            savedNotification.setStatus("FAILED");
            savedNotification.setErrorMessage(e.getMessage());
            notificationRepository.save(savedNotification);
        }
        
        return savedNotification;
    }

    @Override
    public Notification sendSmsNotification(String userId, String phoneNumber, String message) {
        log.info("Sending SMS notification to user: {}, phone: {}", userId, phoneNumber);
        
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .type("SMS")
                .channel("SMS")
                .title("SMS Notification")
                .message(message)
                .content(message)
                .recipientPhone(phoneNumber)
                .category("TRANSACTIONAL")
                .priority("HIGH")
                .status("PENDING")
                .retryCount(0)
                .trackDelivery(true)
                .build();
        
        notification.prePersist();
        
        Notification savedNotification = notificationRepository.save(notification);
        log.info("SMS notification created with ID: {}", savedNotification.getId());
        
        // Send actual SMS
        try {
            smsService.sendSms(phoneNumber, message);
            savedNotification.setStatus("SENT");
            savedNotification.setSentAt(LocalDateTime.now());
            notificationRepository.save(savedNotification);
        } catch (Exception e) {
            log.error("Failed to send SMS, notification ID: {}", savedNotification.getId(), e);
            savedNotification.setStatus("FAILED");
            savedNotification.setErrorMessage(e.getMessage());
            notificationRepository.save(savedNotification);
        }
        
        return savedNotification;
    }

    @Override
    public Notification sendPushNotification(String userId, String deviceToken, String title, String message) {
        log.info("Sending push notification to user: {}", userId);
        
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .type("PUSH")
                .channel("PUSH")
                .title(title)
                .message(message)
                .content(message)
                .recipientDeviceToken(deviceToken)
                .category("SYSTEM")
                .priority("MEDIUM")
                .status("PENDING")
                .retryCount(0)
                .trackDelivery(true)
                .trackOpen(true)
                .build();
        
        notification.prePersist();
        
        Notification savedNotification = notificationRepository.save(notification);
        log.info("Push notification created with ID: {}", savedNotification.getId());
        
        return savedNotification;
    }

    @Override
    public Notification sendInAppNotification(String userId, String title, String message) {
        log.info("Sending in-app notification to user: {}", userId);
        
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .type("IN_APP")
                .channel("IN_APP")
                .title(title)
                .message(message)
                .content(message)
                .category("SYSTEM")
                .priority("LOW")
                .status("PENDING")
                .retryCount(0)
                .trackDelivery(false)
                .build();
        
        notification.prePersist();
        
        Notification savedNotification = notificationRepository.save(notification);
        log.info("In-app notification created with ID: {}", savedNotification.getId());
        
        return savedNotification;
    }

    @Override
    public List<Notification> getNotificationsByUserId(String userId) {
        log.debug("Getting notifications for user: {}", userId);
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotificationsByUserId(String userId) {
        log.debug("Getting unread notifications for user: {}", userId);
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    @Override
    public Notification markAsRead(String notificationId) {
        log.info("Marking notification as read: {}", notificationId);
        
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        
        return notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(String userId) {
        log.info("Marking all notifications as read for user: {}", userId);
        
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        
        for (Notification notification : unreadNotifications) {
            notification.setIsRead(true);
            notification.setReadAt(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    @Override
    public void deleteNotification(String notificationId) {
        log.info("Deleting notification: {}", notificationId);
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public Notification getNotificationById(String notificationId) {
        log.debug("Getting notification by ID: {}", notificationId);
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    @Override
    public Notification updateNotificationStatus(String notificationId, String status) {
        log.info("Updating notification status - id: {}, status: {}", notificationId, status);
        
        Notification notification = getNotificationById(notificationId);
        notification.setStatus(status);
        
        if ("SENT".equals(status)) {
            notification.setSentAt(LocalDateTime.now());
        } else if ("DELIVERED".equals(status)) {
            notification.setDeliveredAt(LocalDateTime.now());
        }
        
        return notificationRepository.save(notification);
    }
}
