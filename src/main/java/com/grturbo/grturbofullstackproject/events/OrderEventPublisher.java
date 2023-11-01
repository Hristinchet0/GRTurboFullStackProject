package com.grturbo.grturbofullstackproject.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public OrderEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishOrderProcessedEvent(String username) {
        OrderProcessedEvent event = new OrderProcessedEvent(this, username);
        eventPublisher.publishEvent(event);
    }
}
