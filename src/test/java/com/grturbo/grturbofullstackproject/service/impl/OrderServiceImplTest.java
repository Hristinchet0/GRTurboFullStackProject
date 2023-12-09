package com.grturbo.grturbofullstackproject.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.grturbo.grturbofullstackproject.events.OrderEventPublisher;
import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.Order;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.enums.OrderStatusEnum;
import com.grturbo.grturbofullstackproject.model.enums.PaymentMethodEnum;
import com.grturbo.grturbofullstackproject.repositority.OrderRepository;
import com.grturbo.grturbofullstackproject.service.ProductService;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {
    @MockBean
    private OrderEventPublisher orderEventPublisher;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @MockBean
    private ProductService productService;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Test
    void testProcessOrder() {
        doNothing().when(shoppingCartService).deleteCartItemsByShoppingCartId(any());

        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setCompanyName("Company Name");
        invoiceData.setCustomer(new User());
        invoiceData.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData.setId(123L);
        invoiceData.setIdentificationNumberUIC("42");
        invoiceData.setPhoneNumber("4105551212");
        invoiceData.setRegisteredAddress("42 Main St");
        invoiceData.setVatRegistration(true);

        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setInvoiceData(invoiceData);
        user.setLastName("Doe");
        user.setOrders(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        InvoiceData invoiceData1 = new InvoiceData();
        invoiceData1.setCompanyName("Company Name");
        invoiceData1.setCustomer(user);
        invoiceData1.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData1.setId(123L);
        invoiceData1.setIdentificationNumberUIC("42");
        invoiceData1.setPhoneNumber("4105551212");
        invoiceData1.setRegisteredAddress("42 Main St");
        invoiceData1.setVatRegistration(true);

        User user1 = new User();
        user1.setAddress("42 Main St");
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setInvoiceData(invoiceData1);
        user1.setLastName("Doe");
        user1.setOrders(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        Order order = new Order();
        order.setAccept(true);
        order.setAdditionalInformation("Additional Information");
        order.setCustomer(user1);
        order.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        order.setOrderDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        order.setOrderDetailList(new ArrayList<>());
        order.setOrderStatus(OrderStatusEnum.PENDING);
        order.setPaymentMethod(PaymentMethodEnum.CASH);
        order.setQuantity(1);
        order.setTax(10.0d);
        order.setTotalPrice(BigDecimal.valueOf(42L));
        when(orderRepository.save(any())).thenReturn(order);
        doNothing().when(orderEventPublisher).publishOrderProcessedEvent(any());

        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setInvoiceData(new InvoiceData());
        user2.setLastName("Doe");
        user2.setOrders(new HashSet<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("4105551212");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        InvoiceData invoiceData2 = new InvoiceData();
        invoiceData2.setCompanyName("Company Name");
        invoiceData2.setCustomer(user2);
        invoiceData2.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData2.setId(123L);
        invoiceData2.setIdentificationNumberUIC("42");
        invoiceData2.setPhoneNumber("4105551212");
        invoiceData2.setRegisteredAddress("42 Main St");
        invoiceData2.setVatRegistration(true);

        User user3 = new User();
        user3.setAddress("42 Main St");
        user3.setCity("Oxford");
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setInvoiceData(invoiceData2);
        user3.setLastName("Doe");
        user3.setOrders(new HashSet<>());
        user3.setPassword("iloveyou");
        user3.setPhoneNumber("4105551212");
        user3.setRoles(new ArrayList<>());
        user3.setUsername("janedoe");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setCustomer(user3);
        shoppingCart.setId(123L);
        shoppingCart.setTotalItems(1000);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(42L));

        InvoiceData invoiceData3 = new InvoiceData();
        invoiceData3.setCompanyName("Company Name");
        invoiceData3.setCustomer(new User());
        invoiceData3.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData3.setId(123L);
        invoiceData3.setIdentificationNumberUIC("42");
        invoiceData3.setPhoneNumber("4105551212");
        invoiceData3.setRegisteredAddress("42 Main St");
        invoiceData3.setVatRegistration(true);

        User user4 = new User();
        user4.setAddress("42 Main St");
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setInvoiceData(invoiceData3);
        user4.setLastName("Doe");
        user4.setOrders(new HashSet<>());
        user4.setPassword("iloveyou");
        user4.setPhoneNumber("4105551212");
        user4.setRoles(new ArrayList<>());
        user4.setUsername("janedoe");

        InvoiceData invoiceData4 = new InvoiceData();
        invoiceData4.setCompanyName("Company Name");
        invoiceData4.setCustomer(user4);
        invoiceData4.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData4.setId(123L);
        invoiceData4.setIdentificationNumberUIC("42");
        invoiceData4.setPhoneNumber("4105551212");
        invoiceData4.setRegisteredAddress("42 Main St");
        invoiceData4.setVatRegistration(true);

        User user5 = new User();
        user5.setAddress("42 Main St");
        user5.setCity("Oxford");
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setInvoiceData(invoiceData4);
        user5.setLastName("Doe");
        user5.setOrders(new HashSet<>());
        user5.setPassword("iloveyou");
        user5.setPhoneNumber("4105551212");
        user5.setRoles(new ArrayList<>());
        user5.setUsername("janedoe");
        Order actualProcessOrderResult = orderServiceImpl.processOrder(shoppingCart, "Additional Information", user5);
        assertSame(order, actualProcessOrderResult);
        assertEquals("42", actualProcessOrderResult.getTotalPrice().toString());
        verify(shoppingCartService).deleteCartItemsByShoppingCartId(any());
        verify(orderRepository).save(any());
        verify(orderEventPublisher).publishOrderProcessedEvent(any());
    }

    @Test
    void testProcessOrder2() {
        doNothing().when(shoppingCartService).deleteCartItemsByShoppingCartId(any());

        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setCompanyName("Company Name");
        invoiceData.setCustomer(new User());
        invoiceData.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData.setId(123L);
        invoiceData.setIdentificationNumberUIC("42");
        invoiceData.setPhoneNumber("4105551212");
        invoiceData.setRegisteredAddress("42 Main St");
        invoiceData.setVatRegistration(true);

        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setInvoiceData(invoiceData);
        user.setLastName("Doe");
        user.setOrders(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        InvoiceData invoiceData1 = new InvoiceData();
        invoiceData1.setCompanyName("Company Name");
        invoiceData1.setCustomer(user);
        invoiceData1.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData1.setId(123L);
        invoiceData1.setIdentificationNumberUIC("42");
        invoiceData1.setPhoneNumber("4105551212");
        invoiceData1.setRegisteredAddress("42 Main St");
        invoiceData1.setVatRegistration(true);

        User user1 = new User();
        user1.setAddress("42 Main St");
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setInvoiceData(invoiceData1);
        user1.setLastName("Doe");
        user1.setOrders(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        Order order = new Order();
        order.setAccept(true);
        order.setAdditionalInformation("Additional Information");
        order.setCustomer(user1);
        order.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        order.setOrderDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        order.setOrderDetailList(new ArrayList<>());
        order.setOrderStatus(OrderStatusEnum.PENDING);
        order.setPaymentMethod(PaymentMethodEnum.CASH);
        order.setQuantity(1);
        order.setTax(10.0d);
        order.setTotalPrice(BigDecimal.valueOf(42L));
        when(orderRepository.save(any())).thenReturn(order);
        doThrow(new IllegalArgumentException()).when(orderEventPublisher).publishOrderProcessedEvent(any());

        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setInvoiceData(new InvoiceData());
        user2.setLastName("Doe");
        user2.setOrders(new HashSet<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("4105551212");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        InvoiceData invoiceData2 = new InvoiceData();
        invoiceData2.setCompanyName("Company Name");
        invoiceData2.setCustomer(user2);
        invoiceData2.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData2.setId(123L);
        invoiceData2.setIdentificationNumberUIC("42");
        invoiceData2.setPhoneNumber("4105551212");
        invoiceData2.setRegisteredAddress("42 Main St");
        invoiceData2.setVatRegistration(true);

        User user3 = new User();
        user3.setAddress("42 Main St");
        user3.setCity("Oxford");
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setInvoiceData(invoiceData2);
        user3.setLastName("Doe");
        user3.setOrders(new HashSet<>());
        user3.setPassword("iloveyou");
        user3.setPhoneNumber("4105551212");
        user3.setRoles(new ArrayList<>());
        user3.setUsername("janedoe");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setCustomer(user3);
        shoppingCart.setId(123L);
        shoppingCart.setTotalItems(1000);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(42L));

        InvoiceData invoiceData3 = new InvoiceData();
        invoiceData3.setCompanyName("Company Name");
        invoiceData3.setCustomer(new User());
        invoiceData3.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData3.setId(123L);
        invoiceData3.setIdentificationNumberUIC("42");
        invoiceData3.setPhoneNumber("4105551212");
        invoiceData3.setRegisteredAddress("42 Main St");
        invoiceData3.setVatRegistration(true);

        User user4 = new User();
        user4.setAddress("42 Main St");
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setInvoiceData(invoiceData3);
        user4.setLastName("Doe");
        user4.setOrders(new HashSet<>());
        user4.setPassword("iloveyou");
        user4.setPhoneNumber("4105551212");
        user4.setRoles(new ArrayList<>());
        user4.setUsername("janedoe");

        InvoiceData invoiceData4 = new InvoiceData();
        invoiceData4.setCompanyName("Company Name");
        invoiceData4.setCustomer(user4);
        invoiceData4.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData4.setId(123L);
        invoiceData4.setIdentificationNumberUIC("42");
        invoiceData4.setPhoneNumber("4105551212");
        invoiceData4.setRegisteredAddress("42 Main St");
        invoiceData4.setVatRegistration(true);

        User user5 = new User();
        user5.setAddress("42 Main St");
        user5.setCity("Oxford");
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setInvoiceData(invoiceData4);
        user5.setLastName("Doe");
        user5.setOrders(new HashSet<>());
        user5.setPassword("iloveyou");
        user5.setPhoneNumber("4105551212");
        user5.setRoles(new ArrayList<>());
        user5.setUsername("janedoe");
        assertThrows(IllegalArgumentException.class,
                () -> orderServiceImpl.processOrder(shoppingCart, "Additional Information", user5));
        verify(shoppingCartService).deleteCartItemsByShoppingCartId(any());
        verify(orderEventPublisher).publishOrderProcessedEvent(any());
    }

    @Test
    void testAcceptOrder() {
        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setCompanyName("Company Name");
        invoiceData.setCustomer(new User());
        invoiceData.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData.setId(123L);
        invoiceData.setIdentificationNumberUIC("42");
        invoiceData.setPhoneNumber("4105551212");
        invoiceData.setRegisteredAddress("42 Main St");
        invoiceData.setVatRegistration(true);

        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setInvoiceData(invoiceData);
        user.setLastName("Doe");
        user.setOrders(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        InvoiceData invoiceData1 = new InvoiceData();
        invoiceData1.setCompanyName("Company Name");
        invoiceData1.setCustomer(user);
        invoiceData1.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData1.setId(123L);
        invoiceData1.setIdentificationNumberUIC("42");
        invoiceData1.setPhoneNumber("4105551212");
        invoiceData1.setRegisteredAddress("42 Main St");
        invoiceData1.setVatRegistration(true);

        User user1 = new User();
        user1.setAddress("42 Main St");
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setInvoiceData(invoiceData1);
        user1.setLastName("Doe");
        user1.setOrders(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        Order order = new Order();
        order.setAccept(true);
        order.setAdditionalInformation("Additional Information");
        order.setCustomer(user1);
        order.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        order.setOrderDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        order.setOrderDetailList(new ArrayList<>());
        order.setOrderStatus(OrderStatusEnum.PENDING);
        order.setPaymentMethod(PaymentMethodEnum.CASH);
        order.setQuantity(1);
        order.setTax(10.0d);
        order.setTotalPrice(BigDecimal.valueOf(42L));

        InvoiceData invoiceData2 = new InvoiceData();
        invoiceData2.setCompanyName("Company Name");
        invoiceData2.setCustomer(new User());
        invoiceData2.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData2.setId(123L);
        invoiceData2.setIdentificationNumberUIC("42");
        invoiceData2.setPhoneNumber("4105551212");
        invoiceData2.setRegisteredAddress("42 Main St");
        invoiceData2.setVatRegistration(true);

        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setInvoiceData(invoiceData2);
        user2.setLastName("Doe");
        user2.setOrders(new HashSet<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("4105551212");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        InvoiceData invoiceData3 = new InvoiceData();
        invoiceData3.setCompanyName("Company Name");
        invoiceData3.setCustomer(user2);
        invoiceData3.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData3.setId(123L);
        invoiceData3.setIdentificationNumberUIC("42");
        invoiceData3.setPhoneNumber("4105551212");
        invoiceData3.setRegisteredAddress("42 Main St");
        invoiceData3.setVatRegistration(true);

        User user3 = new User();
        user3.setAddress("42 Main St");
        user3.setCity("Oxford");
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setInvoiceData(invoiceData3);
        user3.setLastName("Doe");
        user3.setOrders(new HashSet<>());
        user3.setPassword("iloveyou");
        user3.setPhoneNumber("4105551212");
        user3.setRoles(new ArrayList<>());
        user3.setUsername("janedoe");

        Order order1 = new Order();
        order1.setAccept(true);
        order1.setAdditionalInformation("Additional Information");
        order1.setCustomer(user3);
        order1.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        order1.setOrderDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        order1.setOrderDetailList(new ArrayList<>());
        order1.setOrderStatus(OrderStatusEnum.PENDING);
        order1.setPaymentMethod(PaymentMethodEnum.CASH);
        order1.setQuantity(1);
        order1.setTax(10.0d);
        order1.setTotalPrice(BigDecimal.valueOf(42L));
        when(orderRepository.save(any())).thenReturn(order1);
        when(orderRepository.getById(any())).thenReturn(order);
        orderServiceImpl.acceptOrder(123L);
        verify(orderRepository).getById(any());
        verify(orderRepository).save(any());
    }

    @Test
    void testAcceptOrder2() {
        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setCompanyName("Company Name");
        invoiceData.setCustomer(new User());
        invoiceData.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData.setId(123L);
        invoiceData.setIdentificationNumberUIC("42");
        invoiceData.setPhoneNumber("4105551212");
        invoiceData.setRegisteredAddress("42 Main St");
        invoiceData.setVatRegistration(true);

        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setInvoiceData(invoiceData);
        user.setLastName("Doe");
        user.setOrders(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        InvoiceData invoiceData1 = new InvoiceData();
        invoiceData1.setCompanyName("Company Name");
        invoiceData1.setCustomer(user);
        invoiceData1.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData1.setId(123L);
        invoiceData1.setIdentificationNumberUIC("42");
        invoiceData1.setPhoneNumber("4105551212");
        invoiceData1.setRegisteredAddress("42 Main St");
        invoiceData1.setVatRegistration(true);

        User user1 = new User();
        user1.setAddress("42 Main St");
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setInvoiceData(invoiceData1);
        user1.setLastName("Doe");
        user1.setOrders(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        Order order = new Order();
        order.setAccept(true);
        order.setAdditionalInformation("Additional Information");
        order.setCustomer(user1);
        order.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        order.setOrderDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        order.setOrderDetailList(new ArrayList<>());
        order.setOrderStatus(OrderStatusEnum.PENDING);
        order.setPaymentMethod(PaymentMethodEnum.CASH);
        order.setQuantity(1);
        order.setTax(10.0d);
        order.setTotalPrice(BigDecimal.valueOf(42L));
        when(orderRepository.save(any())).thenThrow(new IllegalArgumentException());
        when(orderRepository.getById(any())).thenReturn(order);
        assertThrows(IllegalArgumentException.class, () -> orderServiceImpl.acceptOrder(123L));
        verify(orderRepository).getById(any());
        verify(orderRepository).save(any());
    }

    @Test
    void testGetOrderWithDetails() {
        assertNull(orderServiceImpl.getOrderWithDetails(123L));
    }

    @Test
    void testCancelOrder() {
        assertThrows(IllegalArgumentException.class, () -> orderServiceImpl.cancelOrder(123L));
    }

    @Test
    void testGetOrdersWithDetails() {
        assertTrue(orderServiceImpl.getOrdersWithDetails().isEmpty());
    }

    @Test
    void testSendOrder() {
        assertThrows(IllegalArgumentException.class, () -> orderServiceImpl.sendOrder(123L));
        assertThrows(IllegalArgumentException.class, () -> orderServiceImpl.sendOrder(1L));
    }

    @Test
    void testFindShippedOrdersForLastMonth() {
        assertTrue(orderServiceImpl.findShippedOrdersForLastMonth().isEmpty());
    }

    @Test
    void testCalculateTotalPriceForLastMonth() {
        BigDecimal actualCalculateTotalPriceForLastMonthResult = orderServiceImpl.calculateTotalPriceForLastMonth();
        assertSame(BigDecimal.ZERO, actualCalculateTotalPriceForLastMonthResult);
        assertEquals("0", actualCalculateTotalPriceForLastMonthResult.toString());
    }

    @Test
    void testCalculateAnnualEarnings() {
        BigDecimal actualCalculateAnnualEarningsResult = orderServiceImpl.calculateAnnualEarnings();
        assertSame(BigDecimal.ZERO, actualCalculateAnnualEarningsResult);
        assertEquals("0", actualCalculateAnnualEarningsResult.toString());
    }

    @Test
    void testGetSentOrdersForCurrentMonth() {
        assertEquals(0L, orderServiceImpl.getSentOrdersForCurrentMonth().longValue());
    }

    @Test
    void testGetSentOrdersForCurrentYear() {
        assertEquals(0L, orderServiceImpl.getSentOrdersForCurrentYear().longValue());
    }

    @Test
    void testFindAllOrdersByCustomerId() {
        assertTrue(orderServiceImpl.findAllOrdersByCustomerId(123L).isEmpty());
        assertTrue(orderServiceImpl.findAllOrdersByCustomerId(1L).isEmpty());
    }
}

