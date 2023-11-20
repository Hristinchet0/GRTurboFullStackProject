package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByIdAndShoppingCart_Id(Long cartItemId, Long cartId);

    void deleteCartItemById(Long id);

    Set<CartItem> findByShoppingCart_Id(Long id);
}
