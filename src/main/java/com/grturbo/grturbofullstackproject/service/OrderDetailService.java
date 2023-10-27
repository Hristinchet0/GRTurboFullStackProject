package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.dto.OrderDetailViewDto;
import com.grturbo.grturbofullstackproject.repositority.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDetailViewDto> findByOrderId(Long id) {
        return orderDetailRepository.findByOrderId(id);
    }
}
