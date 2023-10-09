package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderDetailList")
    List<Order> findAllWithOrderDetails();

    @Query("SELECT o FROM Order o JOIN FETCH o.orderDetailList WHERE o.id = :orderId")
    Order findOrderWithDetails(@Param("orderId") Long orderId);

    List<Order> findByOrderDateAfterAndOrderStatus(Date date, String orderStatus);


}
