package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.service.ScheduledService;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledServiceImpl implements ScheduledService {

    private final ShoppingCartService shoppingCartService;

    public ScheduledServiceImpl(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    @Scheduled(cron = "0 00 0 * * *")
    public void deleteAllCartItemsScheduled() {
        shoppingCartService.deleteAllCartItemsInShoppingCarts();
    }
}
