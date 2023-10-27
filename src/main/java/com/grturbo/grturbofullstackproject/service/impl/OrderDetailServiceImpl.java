package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.model.dto.OrderDetailViewDto;
import com.grturbo.grturbofullstackproject.repositority.OrderDetailRepository;
import com.grturbo.grturbofullstackproject.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<OrderDetailViewDto> findByOrderId(Long id) {
        return orderDetailRepository.findByOrderId(id);
    }
}
