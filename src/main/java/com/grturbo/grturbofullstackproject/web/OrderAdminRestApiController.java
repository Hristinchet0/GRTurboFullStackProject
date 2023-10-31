package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.service.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class OrderAdminRestApiController {

    private final OrderServiceImpl orderServiceImpl;

    public OrderAdminRestApiController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<?> cancelOrder(@RequestParam Long id) {
        orderServiceImpl.cancelOrder(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
