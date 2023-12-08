package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.events.OrderEventPublisher;
import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import com.grturbo.grturbofullstackproject.model.entity.Order;
import com.grturbo.grturbofullstackproject.model.entity.OrderDetail;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.enums.OrderStatusEnum;
import com.grturbo.grturbofullstackproject.model.enums.PaymentMethodEnum;
import com.grturbo.grturbofullstackproject.repositority.OrderRepository;
import com.grturbo.grturbofullstackproject.service.OrderService;
import com.grturbo.grturbofullstackproject.service.ProductService;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;
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

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ShoppingCartService shoppingCartService;

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final OrderEventPublisher orderEventPublisher;

    public OrderServiceImpl(ShoppingCartService shoppingCartService,
                            OrderRepository orderRepository,
                            ProductService productService,
                            OrderEventPublisher orderEventPublisher) {
        this.shoppingCartService = shoppingCartService;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderEventPublisher = orderEventPublisher;
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
            Product product = productService.getProductByID(productId);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetailList.add(orderDetail);
        }

        order.setOrderDetailList(orderDetailList);
        shoppingCart.getCartItems().clear();
        shoppingCartService.deleteCartItemsByShoppingCartId(shoppingCart.getId());

        String fullName = user.getFirstName() + " " + user.getLastName();

        orderEventPublisher.publishOrderProcessedEvent(fullName);

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

        String userEmail = order.getCustomer().getEmail();
        Long orderId = order.getId();

        orderEventPublisher.publishOrderStatusEvent(orderId, userEmail);

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

    @Override
    public Set<Order> findAllOrdersByCustomerId(Long id) {
        return orderRepository.findAllByCustomer_Id(id);
    }

    private List<Order> findShippedOrdersForLastYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);

        Date lastYear = calendar.getTime();

        return orderRepository.findByOrderDateAfterAndOrderStatus(lastYear, OrderStatusEnum.SHIPPED);
    }


}