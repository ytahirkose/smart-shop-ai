package com.smartshopai.notification.domain.service;

import com.smartshopai.notification.domain.entity.Notification;

import java.util.List;

/**
 * Domain service for notification operations
 */
public interface NotificationService {

    /**
     * Send email notification
     */
    Notification sendEmailNotification(String userId, String email, String subject, String content);

    /**
     * Send SMS notification
     */
    Notification sendSmsNotification(String userId, String phoneNumber, String message);

    /**
     * Send push notification
     */
    Notification sendPushNotification(String userId, String deviceToken, String title, String message);

    /**
     * Send in-app notification
     */
    Notification sendInAppNotification(String userId, String title, String message);

    /**
     * Get notifications by user ID
     */
    List<Notification> getNotificationsByUserId(String userId);

    /**
     * Get unread notifications by user ID
     */
    List<Notification> getUnreadNotificationsByUserId(String userId);

    /**
     * Mark notification as read
     */
    Notification markAsRead(String notificationId);

    /**
     * Mark all notifications as read for user
     */
    void markAllAsRead(String userId);

    /**
     * Delete notification
     */
    void deleteNotification(String notificationId);

    /**
     * Get notification by ID
     */
    Notification getNotificationById(String notificationId);

    /**
     * Update notification status
     */
    Notification updateNotificationStatus(String notificationId, String status);
}
