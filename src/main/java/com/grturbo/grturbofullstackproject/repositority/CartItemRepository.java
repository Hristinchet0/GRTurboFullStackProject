package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByIdAndShoppingCart_Id(Long cartItemId, Long cartId);

    @Modifying
    @Query("DELETE FROM CartItem " +
            "WHERE id = :id")
    void deleteById(@NotNull Long id);


}
