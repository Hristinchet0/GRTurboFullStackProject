package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.*;
import com.grturbo.grturbofullstackproject.repositority.OrderDetailRepository;
import com.grturbo.grturbofullstackproject.repositority.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderDetailRepository orderDetailRepository;

    private final ShoppingCartService shoppingCartService;

    private final OrderRepository orderRepository;

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
}
