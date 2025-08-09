package com.smartshopai.user.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Event published when a user is updated
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatedEvent {
    
    private String userId;
    private String username;
    private String email;
    private LocalDateTime updatedAt;
    private String eventType = "USER_UPDATED";
}
