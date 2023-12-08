package com.grturbo.grturbofullstackproject.events;

import org.springframework.context.ApplicationEvent;

public class OrderStatusEvent extends ApplicationEvent {

    private final Long orderId;

    private final String userEmail;

    public OrderStatusEvent(Object source, Long orderId, String userEmail) {
        super(source);
        this.orderId = orderId;
        this.userEmail = userEmail;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
