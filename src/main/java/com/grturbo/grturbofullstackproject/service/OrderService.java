package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.Order;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    Order processOrder(ShoppingCart shoppingCart, String additionalInformation, User user);

    @Transactional
    void acceptOrder(Long id);

    Order getOrderWithDetails(Long id);

    void cancelOrder(Long id);

    List<Order> getOrdersWithDetails();

    void sendOrder(Long id);

    List<Order> findShippedOrdersForLastMonth();

    Double calculateTotalPriceForLastMonth();

    Double calculateAnnualEarnings();

    Long getSentOrdersForCurrentMonth();

    Long getSentOrdersForCurrentYear();
}
