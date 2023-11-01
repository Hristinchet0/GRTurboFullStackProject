package com.grturbo.grturbofullstackproject.events;

import org.springframework.context.ApplicationEvent;

public class OrderProcessedEvent extends ApplicationEvent {

    private final String username;

    public OrderProcessedEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
