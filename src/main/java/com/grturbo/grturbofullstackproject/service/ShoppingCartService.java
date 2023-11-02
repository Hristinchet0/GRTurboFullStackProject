package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface ShoppingCartService {

    ShoppingCart addItemToCart(Product product, Integer quantity, User user);

    ShoppingCart updateItemInCart(Product product, Integer quantity, User user);

    @Transactional
    ShoppingCart deleteItemFromCart(Long cartItemId, User user);

    void deleteCartItemsByShoppingCartId(Long id);

    void deleteAllCartItemsInShoppingCarts();

    ShoppingCart findByUserId(Long id);
}
