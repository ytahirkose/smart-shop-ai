package com.smartshopai.user.domain.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishUserCreatedEvent(UserCreatedEvent event) {
        log.info("Publishing user created event for userId: {}", event.getUserId());
        eventPublisher.publishEvent(event);
    }

    public void publishUserUpdatedEvent(UserUpdatedEvent event) {
        log.info("Publishing user updated event for userId: {}", event.getUserId());
        eventPublisher.publishEvent(event);
    }
}
