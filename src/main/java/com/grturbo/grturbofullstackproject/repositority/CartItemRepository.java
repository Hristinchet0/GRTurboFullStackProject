package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByIdAndShoppingCart_Id(Long cartItemId, Long cartId);

    @Modifying
    @Query("DELETE FROM CartItem c " +
            "WHERE c.id = :id")
    void deleteCartItemById(Long id);

    Set<CartItem> findByShoppingCart_Id(Long shoppingCartId);
}
