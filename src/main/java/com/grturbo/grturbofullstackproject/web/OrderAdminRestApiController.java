package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class OrderAdminRestApiController {

    private final OrderService orderService;

    public OrderAdminRestApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<?> cancelOrder(@RequestParam Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
