package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import com.grturbo.grturbofullstackproject.repositority.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void removeProductById(Long id) {
        Optional<CartItem> byId = this.cartItemRepository.findById(id);
        this.cartItemRepository.delete(byId.get());
    }
}
