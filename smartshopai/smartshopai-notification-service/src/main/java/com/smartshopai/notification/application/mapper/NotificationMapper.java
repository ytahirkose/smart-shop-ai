package com.smartshopai.notification.application.mapper;

import com.smartshopai.notification.application.dto.request.SendNotificationRequest;
import com.smartshopai.notification.application.dto.response.NotificationResponse;
import com.smartshopai.notification.domain.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Builder;

import java.util.List;

/**
 * Mapper for Notification entities and DTOs
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface NotificationMapper {
    NotificationResponse toResponse(Notification notification);

    List<NotificationResponse> toResponseList(List<Notification> notifications);
}
