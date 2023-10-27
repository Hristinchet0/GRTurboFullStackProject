package com.grturbo.grturbofullstackproject.service;

import org.springframework.scheduling.annotation.Scheduled;

public interface ScheduledService {
    @Scheduled(cron = "0 12 10 * * ?")
    void deleteAllCartItemsScheduled();
}
