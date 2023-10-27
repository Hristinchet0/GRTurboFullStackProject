package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.service.ScheduledService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledServiceImpl implements ScheduledService {

    private final CartItemServiceImpl cartItemService;

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    public ScheduledServiceImpl(CartItemServiceImpl cartItemService, ShoppingCartServiceImpl shoppingCartServiceImpl) {
        this.cartItemService = cartItemService;
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
    }

    @Override
    @Scheduled(cron = "0 12 10 * * ?")
    public void deleteAllCartItemsScheduled() {
        shoppingCartServiceImpl.deleteAllCartItemsInShoppingCarts();
    }
}
