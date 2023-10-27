package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.repositority.CartItemRepository;
import com.grturbo.grturbofullstackproject.service.CartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

}
