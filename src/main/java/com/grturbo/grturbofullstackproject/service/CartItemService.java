package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import com.grturbo.grturbofullstackproject.repositority.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void deleteAllCartItems() {
        System.out.println("Scheduled is activated");
        List<CartItem> cartItems = cartItemRepository.findAll();

        cartItemRepository.deleteAll(cartItems);
        System.out.println("Scheduled deleted all");
    }
}
