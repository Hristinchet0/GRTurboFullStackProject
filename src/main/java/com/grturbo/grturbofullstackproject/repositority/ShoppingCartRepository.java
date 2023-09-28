package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
