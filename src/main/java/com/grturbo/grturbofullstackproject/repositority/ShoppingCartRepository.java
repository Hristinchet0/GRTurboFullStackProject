package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("SELECT DISTINCT sc FROM ShoppingCart sc JOIN sc.cartItems ci")
    List<ShoppingCart> findAllCartsWithItems();

    ShoppingCart findShoppingCartByCustomer_Id(Long id);
}
