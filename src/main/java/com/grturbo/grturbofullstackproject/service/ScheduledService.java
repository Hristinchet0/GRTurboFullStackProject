package com.grturbo.grturbofullstackproject.service;

import org.springframework.scheduling.annotation.Scheduled;

public interface ScheduledService {

    @Scheduled(cron = "0 00 0 * * *")
    void deleteAllCartItemsScheduled();
}
