package com.grturbo.grturbofullstackproject.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    private final CartItemService cartItemService;

    private final ShoppingCartService shoppingCartService;

    public ScheduledService(CartItemService cartItemService, ShoppingCartService shoppingCartService) {
        this.cartItemService = cartItemService;
        this.shoppingCartService = shoppingCartService;
    }

    @Scheduled(cron = "0 12 10 * * ?")
    public void deleteAllCartItemsScheduled() {
        shoppingCartService.deleteAllCartItemsInShoppingCarts();
    }
}
