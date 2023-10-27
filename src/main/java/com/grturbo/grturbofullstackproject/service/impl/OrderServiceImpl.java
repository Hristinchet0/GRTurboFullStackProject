package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import com.grturbo.grturbofullstackproject.model.entity.Order;
import com.grturbo.grturbofullstackproject.model.entity.OrderDetail;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.repositority.OrderRepository;
import com.grturbo.grturbofullstackproject.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    private final OrderRepository orderRepository;

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ProductServiceImpl productServiceImpl;

    public OrderServiceImpl(ShoppingCartServiceImpl shoppingCartServiceImpl, OrderRepository orderRepository, ProductServiceImpl productServiceImpl) {
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
        this.orderRepository = orderRepository;
        this.productServiceImpl = productServiceImpl;
    }

    @Transactional
    @Override
    public Order saveOrder(ShoppingCart shoppingCart, String additionalInformation) {
        Order order = new Order();

        order.setOrderStatus("PENDING");
        order.setOrderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setPaymentMethod("CASH");
        order.setQuantity(shoppingCart.getTotalItems());
        order.setAccept(false);
        order.setAdditionalInformation(additionalInformation);

        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (CartItem item : shoppingCart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            Long productId = item.getProduct().getId();
            Product product = productServiceImpl.getProductByID(productId);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetailList.add(orderDetail);
        }

        order.setOrderDetailList(orderDetailList);
        shoppingCart.getCartItems().clear();
        shoppingCartServiceImpl.deleteCartItemsByShoppingCartId(shoppingCart.getId());

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setOrderStatus("ACCEPTED");
        order.setAccept(true);
        orderRepository.save(order);
    }

    @Override
    public Order getOrderWithDetails(Long id) {
        return orderRepository.findOrderWithDetails(id);
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
        order.setAccept(false);
        order.setOrderStatus("CANCELLED");
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersWithDetails() {
        return orderRepository.findAllWithOrderDetails();
    }

    @Override
    public void sendOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
        order.setOrderStatus("SHIPPED");
        orderRepository.save(order);
    }

    @Override
    public List<Order> findShippedOrdersForLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        Date lastMonth = calendar.getTime();

        return orderRepository.findByOrderDateAfterAndOrderStatus(lastMonth, "SHIPPED");
    }

    @Override
    public Double calculateTotalPriceForLastMonth() {
        List<Order> shippedOrdersForLastMonth = findShippedOrdersForLastMonth();

        return shippedOrdersForLastMonth.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    @Override
    public Double calculateAnnualEarnings() {
        List<Order> shippedOrdersForLastYear = findShippedOrdersForLastYear();

        return shippedOrdersForLastYear.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    @Override
    public Long getSentOrdersForCurrentMonth() {
        LocalDate startDateTime = LocalDate.now().withDayOfMonth(1);
        LocalDate endDateTime = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        return orderRepository.countByOrderDateBetweenAndOrderStatus(
                Timestamp.valueOf(startDateTime.atStartOfDay()), Timestamp.valueOf(endDateTime.atStartOfDay()), "SHIPPED"
        );
    }

    @Override
    public Long getSentOrdersForCurrentYear() {
        LocalDate startDateTime = LocalDate.now().withDayOfYear(1);
        LocalDate endDateTime = LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear());

        return orderRepository.countByOrderDateBetweenAndOrderStatus(
                Timestamp.valueOf(startDateTime.atStartOfDay()), Timestamp.valueOf(endDateTime.atStartOfDay()), "SHIPPED"
        );
    }

    private List<Order> findShippedOrdersForLastYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);

        Date lastYear = calendar.getTime();

        return orderRepository.findByOrderDateAfterAndOrderStatus(lastYear, "SHIPPED");
    }

}