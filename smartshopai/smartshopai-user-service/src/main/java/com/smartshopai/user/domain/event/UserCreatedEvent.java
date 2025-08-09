package com.smartshopai.user.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Event published when a new user is created
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {
    
    private String userId;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private String eventType = "USER_CREATED";
}
