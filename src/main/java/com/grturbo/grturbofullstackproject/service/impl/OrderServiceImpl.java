package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.events.OrderEventPublisher;
import com.grturbo.grturbofullstackproject.model.entity.*;
import com.grturbo.grturbofullstackproject.model.enums.OrderStatusEnum;
import com.grturbo.grturbofullstackproject.model.enums.PaymentMethodEnum;
import com.grturbo.grturbofullstackproject.repositority.OrderRepository;
import com.grturbo.grturbofullstackproject.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    private final OrderRepository orderRepository;

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ProductServiceImpl productServiceImpl;

    private final EmailServiceImpl emailServiceImpl;

    private final UserServiceImpl userServiceImpl;

    private final OrderEventPublisher eventPublisher;

    public OrderServiceImpl(ShoppingCartServiceImpl shoppingCartServiceImpl, OrderRepository orderRepository, ProductServiceImpl productServiceImpl, EmailServiceImpl emailServiceImpl, UserServiceImpl userServiceImpl, OrderEventPublisher eventPublisher) {
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
        this.orderRepository = orderRepository;
        this.productServiceImpl = productServiceImpl;
        this.emailServiceImpl = emailServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public Order processOrder(ShoppingCart shoppingCart, String additionalInformation, User user) {
        Order order = new Order();

        order.setOrderStatus(OrderStatusEnum.PENDING);
        order.setOrderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setPaymentMethod(PaymentMethodEnum.CASH);
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

        String fullName = user.getFirstName() + " " + user.getLastName();

        eventPublisher.publishOrderProcessedEvent(fullName);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setOrderStatus(OrderStatusEnum.ACCEPTED);
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
        order.setOrderStatus(OrderStatusEnum.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersWithDetails() {
        return orderRepository.findAllWithOrderDetails();
    }

    @Override
    public void sendOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
        order.setOrderStatus(OrderStatusEnum.SHIPPED);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findShippedOrdersForLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        Date lastMonth = calendar.getTime();

        return orderRepository.findByOrderDateAfterAndOrderStatus(lastMonth, OrderStatusEnum.SHIPPED);
    }

    @Override
    public BigDecimal calculateTotalPriceForLastMonth() {
        List<Order> shippedOrdersForLastMonth = findShippedOrdersForLastMonth();

        return shippedOrdersForLastMonth.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateAnnualEarnings() {
        List<Order> shippedOrdersForLastYear = findShippedOrdersForLastYear();

        return shippedOrdersForLastYear.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Long getSentOrdersForCurrentMonth() {
        LocalDate startDateTime = LocalDate.now().withDayOfMonth(1);
        LocalDate endDateTime = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        return orderRepository.countByOrderDateBetweenAndOrderStatus(
                Timestamp.valueOf(startDateTime.atStartOfDay()), Timestamp.valueOf(endDateTime.atStartOfDay()), OrderStatusEnum.SHIPPED
        );
    }

    @Override
    public Long getSentOrdersForCurrentYear() {
        LocalDate startDateTime = LocalDate.now().withDayOfYear(1);
        LocalDate endDateTime = LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear());

        return orderRepository.countByOrderDateBetweenAndOrderStatus(
                Timestamp.valueOf(startDateTime.atStartOfDay()), Timestamp.valueOf(endDateTime.atStartOfDay()), OrderStatusEnum.SHIPPED
        );
    }

    private List<Order> findShippedOrdersForLastYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);

        Date lastYear = calendar.getTime();

        return orderRepository.findByOrderDateAfterAndOrderStatus(lastYear, OrderStatusEnum.SHIPPED);
    }

    public Set<Order> findAllOrdersByCustomerId(Long id) {
        return orderRepository.findAllByCustomer_Id(id);
    }

}