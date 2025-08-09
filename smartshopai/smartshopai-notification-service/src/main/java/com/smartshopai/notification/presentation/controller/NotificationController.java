package com.smartshopai.notification.presentation.controller;

import com.smartshopai.notification.application.dto.request.SendEmailRequest;
import com.smartshopai.notification.application.dto.request.SendNotificationRequest;
import com.smartshopai.notification.application.dto.request.SendPushRequest;
import com.smartshopai.notification.application.dto.request.SendSmsRequest;
import com.smartshopai.notification.application.dto.response.NotificationResponse;
import com.smartshopai.notification.application.service.NotificationApplicationService;
import com.smartshopai.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for notification operations
 */
@Slf4j
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationApplicationService notificationApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<NotificationResponse>> sendNotification(
            @Valid @RequestBody SendNotificationRequest request) {
        log.info("Sending notification to user: {}", request.getUserId());
        
        NotificationResponse response = notificationApplicationService.sendNotification(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/email")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<NotificationResponse>> sendEmail(
            @Valid @RequestBody SendEmailRequest request) {
        log.info("Sending email to: {}", request.getTo());
        
        NotificationResponse response = notificationApplicationService.sendEmail(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/sms")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<NotificationResponse>> sendSms(
            @Valid @RequestBody SendSmsRequest request) {
        log.info("Sending SMS to: {}", request.getTo());
        
        NotificationResponse response = notificationApplicationService.sendSms(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/push")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<NotificationResponse>> sendPushNotification(
            @Valid @RequestBody SendPushRequest request) {
        log.info("Sending push notification to user: {}", request.getUserId());
        
        NotificationResponse response = notificationApplicationService.sendPushNotification(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<NotificationResponse>>> getUserNotifications(
            @PathVariable String userId) {
        log.debug("Getting notifications for user: {}", userId);
        
        List<NotificationResponse> response = notificationApplicationService.getUserNotifications(userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/user/{userId}/unread")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<NotificationResponse>>> getUnreadNotifications(
            @PathVariable String userId) {
        log.debug("Getting unread notifications for user: {}", userId);
        
        List<NotificationResponse> response = notificationApplicationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PutMapping("/{notificationId}/read")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<String>> markAsRead(@PathVariable String notificationId) {
        log.debug("Marking notification as read: {}", notificationId);
        
        notificationApplicationService.markAsRead(notificationId);
        return ResponseEntity.ok(BaseResponse.success("Notification marked as read"));
    }

    @PutMapping("/user/{userId}/read-all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<String>> markAllAsRead(@PathVariable String userId) {
        log.debug("Marking all notifications as read for user: {}", userId);
        
        notificationApplicationService.markAllAsRead(userId);
        return ResponseEntity.ok(BaseResponse.success("All notifications marked as read"));
    }

    @DeleteMapping("/{notificationId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<String>> deleteNotification(@PathVariable String notificationId) {
        log.info("Deleting notification: {}", notificationId);
        
        notificationApplicationService.deleteNotification(notificationId);
        return ResponseEntity.ok(BaseResponse.success("Notification deleted successfully"));
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<String>> deleteAllNotifications(@PathVariable String userId) {
        log.info("Deleting all notifications for user: {}", userId);
        
        notificationApplicationService.deleteAllNotifications(userId);
        return ResponseEntity.ok(BaseResponse.success("All notifications deleted successfully"));
    }

    @GetMapping("/{notificationId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<NotificationResponse>> getNotificationById(
            @PathVariable String notificationId) {
        log.debug("Getting notification by ID: {}", notificationId);
        
        NotificationResponse response = notificationApplicationService.getNotificationById(notificationId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}
