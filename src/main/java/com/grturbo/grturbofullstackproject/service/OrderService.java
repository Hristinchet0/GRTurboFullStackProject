package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import com.grturbo.grturbofullstackproject.model.entity.Order;
import com.grturbo.grturbofullstackproject.model.entity.OrderDetail;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.repositority.OrderRepository;
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
public class OrderService {

    private final ShoppingCartService shoppingCartService;

    private final OrderRepository orderRepository;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final ProductService productService;

    public OrderService(ShoppingCartService shoppingCartService, OrderRepository orderRepository, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public Order saveOrder(ShoppingCart cart, String additionalInformation) {
        Order order = new Order();

        order.setOrderStatus("PENDING");
        order.setOrderDate(new Date());
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrice());
        order.setPaymentMethod("CASH");
        order.setQuantity(cart.getTotalItems());
        order.setAccept(false);
        order.setAdditionalInformation(additionalInformation);

        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            Long productId = item.getProduct().getId();
            Product product = productService.getProductByID(productId);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetailList.add(orderDetail);
        }

        order.setOrderDetailList(orderDetailList);
        shoppingCartService.deleteCartById(cart.getId());
//        shoppingCartService.clearCart(order.getCustomer().getShoppingCart());
        return orderRepository.save(order);
    }

    public List<Order> findALlOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setOrderStatus("ACCEPTED");
        order.setAccept(true);
        orderRepository.save(order);
    }

    public Order getOrderWithDetails(Long id) {
        return orderRepository.findOrderWithDetails(id);
    }

    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
        order.setAccept(false);
        order.setOrderStatus("CANCELLED");
        orderRepository.save(order);
    }

    public List<Order> getOrdersWithDetails() {
        List<Order> orders = orderRepository.findAllWithOrderDetails();
        return orders;
    }

    public void sendOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
        order.setOrderStatus("SHIPPED");
        orderRepository.save(order);
    }

    public List<Order> findShippedOrdersForLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        Date lastMonth = calendar.getTime();

        return orderRepository.findByOrderDateAfterAndOrderStatus(lastMonth, "SHIPPED");
    }

    public double calculateTotalPriceForLastMonth() {
        List<Order> shippedOrdersForLastMonth = findShippedOrdersForLastMonth();

        return shippedOrdersForLastMonth.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    public List<Order> findShippedOrdersForLastYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);

        Date lastYear = calendar.getTime();

        return orderRepository.findByOrderDateAfterAndOrderStatus(lastYear, "SHIPPED");
    }

    public double calculateAnnualEarnings() {
        List<Order> shippedOrdersForLastYear = findShippedOrdersForLastYear();

        double annualEarnings = shippedOrdersForLastYear.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();

        return annualEarnings;
    }

    public long getSentOrdersForCurrentMonth() {
        LocalDate startDateTime = LocalDate.now().withDayOfMonth(1);
        LocalDate endDateTime = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        return orderRepository.countByOrderDateBetweenAndOrderStatus(
                Timestamp.valueOf(startDateTime.atStartOfDay()), Timestamp.valueOf(endDateTime.atStartOfDay()), "SHIPPED"
        );
    }

    public long getSentOrdersForCurrentYear() {
        LocalDate startDateTime = LocalDate.now().withDayOfYear(1);
        LocalDate endDateTime = LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear());

        return orderRepository.countByOrderDateBetweenAndOrderStatus(
                Timestamp.valueOf(startDateTime.atStartOfDay()), Timestamp.valueOf(endDateTime.atStartOfDay()), "SHIPPED"
        );
    }
}