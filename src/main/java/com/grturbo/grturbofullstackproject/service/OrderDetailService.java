package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.dto.OrderDetailViewDto;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailViewDto> findByOrderId(Long id);
}
