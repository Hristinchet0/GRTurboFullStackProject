package com.grturbo.grturbofullstackproject.events;

import com.grturbo.grturbofullstackproject.service.impl.EmailServiceImpl;
import com.grturbo.grturbofullstackproject.service.impl.UserServiceImpl;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final UserServiceImpl userServiceImpl;

    private final EmailServiceImpl emailServiceImpl;

    public OrderEventListener(UserServiceImpl userServiceImpl, EmailServiceImpl emailServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.emailServiceImpl = emailServiceImpl;
    }

    @EventListener
    public void handleOrderProcessedEvent(OrderProcessedEvent event) {
        String username = event.getUsername();

        String toAdminEmail = "bansko.sport@gmail.com";
        String subject = "Получихте нова поръчка!";
        String text = "Получихте нова от: " + username + ".";

        emailServiceImpl.sendEmail(toAdminEmail, subject, text);
    }
}
