package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.*;
import com.grturbo.grturbofullstackproject.repositority.OrderDetailRepository;
import com.grturbo.grturbofullstackproject.repositority.OrderRepository;
import com.grturbo.grturbofullstackproject.web.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderDetailRepository orderDetailRepository;

    private final ShoppingCartService shoppingCartService;

    private final OrderRepository orderRepository;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final ProductService productService;

    public OrderService(OrderDetailRepository orderDetailRepository, ShoppingCartService shoppingCartService, OrderRepository orderRepository, ProductService productService) {
        this.orderDetailRepository = orderDetailRepository;
        this.shoppingCartService = shoppingCartService;
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public Order saveOrder(ShoppingCart cart) {
        Order order = new Order();

        order.setOrderStatus("PENDING");
        order.setOrderDate(new Date());
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrice());
        order.setPaymentMethod("CASH");
        order.setQuantity(cart.getTotalItems());
        order.setAccept(false);

        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            Long productId = item.getProduct().getId();
            Product product = productService.getProductByID(productId);
            orderDetail.setProduct(product);
            orderDetailList.add(orderDetail);
        }

        order.setOrderDetailList(orderDetailList);
        shoppingCartService.deleteCartById(cart.getId());
        return orderRepository.save(order);
    }

    public Order acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setAccept(true);
        order.setDeliveryDate(new Date());
        order.setOrderStatus("SHIPPING");
        return orderRepository.save(order);
    }
}
