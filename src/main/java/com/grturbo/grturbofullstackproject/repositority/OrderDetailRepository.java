package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.dto.OrderDetailViewDto;
import com.grturbo.grturbofullstackproject.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


    @Query(value = "SELECT new com.grturbo.grturbofullstackproject.model.dto.OrderDetailViewDto(" +
            "o.product.imgUrl, o.product.name, o.product.price, o.quantity, o.order.totalPrice) FROM OrderDetail o " +
            "WHERE o.order.id = :id")
    List<OrderDetailViewDto> findByOrderId(Long id);
}
