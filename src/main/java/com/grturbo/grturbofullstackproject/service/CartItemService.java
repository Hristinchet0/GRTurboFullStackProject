package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.repositority.CartItemRepository;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

}
