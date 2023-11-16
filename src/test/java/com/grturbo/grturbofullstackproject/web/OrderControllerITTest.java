package com.grturbo.grturbofullstackproject.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.Order;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.enums.OrderStatusEnum;
import com.grturbo.grturbofullstackproject.model.enums.PaymentMethodEnum;
import com.grturbo.grturbofullstackproject.service.EmailService;
import com.grturbo.grturbofullstackproject.service.OrderDetailService;
import com.grturbo.grturbofullstackproject.service.OrderService;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;
import com.grturbo.grturbofullstackproject.service.UserService;
import com.sun.security.auth.UserPrincipal;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
class OrderControllerITTest {

    @MockBean
    private EmailService emailService;

    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderDetailService orderDetailService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private UserService userService;

    @Test
    void testCheckOut() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/check-out");
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderDetailViewDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testCheckOut2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/check-out", "Uri Vars");
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderDetailViewDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testCheckOut3() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCheckOut4() throws Exception {
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
        Optional<User> ofResult = Optional.of(user1);
        when(userService.findByEmail(any())).thenReturn(ofResult);

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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setCustomer(user3);
        shoppingCart.setId(123L);
        shoppingCart.setTotalItems(1000);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(42L));
        when(shoppingCartService.findByUserId(any())).thenReturn(shoppingCart);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/check-out");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("grandTotal", "orderDetailViewDto", "page", "shoppingCart", "title", "user"))
                .andExpect(MockMvcResultMatchers.view().name("checkout"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("checkout"));
    }

    @Test
    void testCheckOut5() throws Exception {
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
        user1.setAddress(null);
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
        Optional<User> ofResult = Optional.of(user1);
        when(userService.findByEmail(any())).thenReturn(ofResult);

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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setCustomer(user3);
        shoppingCart.setId(123L);
        shoppingCart.setTotalItems(1000);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(42L));
        when(shoppingCartService.findByUserId(any())).thenReturn(shoppingCart);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/check-out");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("information", "orderDetailViewDto", "page", "title", "user"))
                .andExpect(MockMvcResultMatchers.view().name("user-profile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user-profile"));
    }

    @Test
    void testCheckOut6() throws Exception {
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
        user1.setCity(null);
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
        Optional<User> ofResult = Optional.of(user1);
        when(userService.findByEmail(any())).thenReturn(ofResult);

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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setCustomer(user3);
        shoppingCart.setId(123L);
        shoppingCart.setTotalItems(1000);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(42L));
        when(shoppingCartService.findByUserId(any())).thenReturn(shoppingCart);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/check-out");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("information", "orderDetailViewDto", "page", "title", "user"))
                .andExpect(MockMvcResultMatchers.view().name("user-profile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user-profile"));
    }

    @Test
    void testCreateOrder() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/add-order");
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderDetailViewDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testCreateOrder2() throws Exception {
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
        Optional<User> ofResult = Optional.of(user1);
        when(userService.findByEmail(any())).thenReturn(ofResult);

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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setCustomer(user3);
        shoppingCart.setId(123L);
        shoppingCart.setTotalItems(1000);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(42L));
        when(shoppingCartService.findByUserId(any())).thenReturn(shoppingCart);

        InvoiceData invoiceData4 = new InvoiceData();
        invoiceData4.setCompanyName("Company Name");
        invoiceData4.setCustomer(new User());
        invoiceData4.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData4.setId(123L);
        invoiceData4.setIdentificationNumberUIC("42");
        invoiceData4.setPhoneNumber("4105551212");
        invoiceData4.setRegisteredAddress("42 Main St");
        invoiceData4.setVatRegistration(true);

        User user4 = new User();
        user4.setAddress("42 Main St");
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setInvoiceData(invoiceData4);
        user4.setLastName("Doe");
        user4.setOrders(new HashSet<>());
        user4.setPassword("iloveyou");
        user4.setPhoneNumber("4105551212");
        user4.setRoles(new ArrayList<>());
        user4.setUsername("janedoe");

        InvoiceData invoiceData5 = new InvoiceData();
        invoiceData5.setCompanyName("Company Name");
        invoiceData5.setCustomer(user4);
        invoiceData5.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData5.setId(123L);
        invoiceData5.setIdentificationNumberUIC("42");
        invoiceData5.setPhoneNumber("4105551212");
        invoiceData5.setRegisteredAddress("42 Main St");
        invoiceData5.setVatRegistration(true);

        User user5 = new User();
        user5.setAddress("42 Main St");
        user5.setCity("Oxford");
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setInvoiceData(invoiceData5);
        user5.setLastName("Doe");
        user5.setOrders(new HashSet<>());
        user5.setPassword("iloveyou");
        user5.setPhoneNumber("4105551212");
        user5.setRoles(new ArrayList<>());
        user5.setUsername("janedoe");

        Order order = new Order();
        order.setAccept(true);
        order.setAdditionalInformation("Additional Information");
        order.setCustomer(user5);
        order.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        order.setOrderDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        order.setOrderDetailList(new ArrayList<>());
        order.setOrderStatus(OrderStatusEnum.PENDING);
        order.setPaymentMethod(PaymentMethodEnum.CASH);
        order.setQuantity(1);
        order.setTax(10.0d);
        order.setTotalPrice(BigDecimal.valueOf(42L));
        when(orderService.processOrder(any(), any(), any())).thenReturn(order);
        doNothing().when(emailService).sendEmail(any(), any(), any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/add-order");
        postResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("order", "orderDetailViewDto", "page", "title"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/successful-order"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/successful-order?title=Order+Detail&page=Order+Detail"));
    }

    @Test
    void testGetOrderDetails() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order-details/{id}", 123L);
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderDetailViewDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testGetOrderDetails2() throws Exception {
        when(orderDetailService.findByOrderId(any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/order-details/{id}", 123L);
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("order", "orderDetailViewDto"))
                .andExpect(MockMvcResultMatchers.view().name("order-detail"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("order-detail"));
    }

    @Test
    void testGetOrders() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders");
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderDetailViewDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testGetOrders2() throws Exception {
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
        Optional<User> ofResult = Optional.of(user1);
        when(userService.findByEmail(any())).thenReturn(ofResult);
        when(orderService.findAllOrdersByCustomerId(any())).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/orders");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderDetailViewDto", "orders", "page", "title"))
                .andExpect(MockMvcResultMatchers.view().name("order"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("order"));
    }

    @Test
    void testSuccessful() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/successful-order");
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderDetailViewDto"))
                .andExpect(MockMvcResultMatchers.view().name("success"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("success"));
    }

    @Test
    void testSuccessful2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/successful-order");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderDetailViewDto"))
                .andExpect(MockMvcResultMatchers.view().name("success"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("success"));
    }
}

