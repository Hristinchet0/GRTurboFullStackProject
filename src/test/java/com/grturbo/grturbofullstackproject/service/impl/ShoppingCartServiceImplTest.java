package com.grturbo.grturbofullstackproject.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.CartItemRepository;
import com.grturbo.grturbofullstackproject.repositority.ShoppingCartRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ShoppingCartServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ShoppingCartServiceImplTest {
    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @Test
    void testDeleteItemFromCart() {
        Category category = new Category();
        category.setId(123L);
        category.setName("Name");

        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setImgUrl("https://example.org/example");
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));

        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setInvoiceData(new InvoiceData());
        user.setLastName("Doe");
        user.setOrders(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setCompanyName("Company Name");
        invoiceData.setCustomer(user);
        invoiceData.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData.setId(123L);
        invoiceData.setIdentificationNumberUIC("42");
        invoiceData.setPhoneNumber("4105551212");
        invoiceData.setRegisteredAddress("42 Main St");
        invoiceData.setVatRegistration(true);

        User user1 = new User();
        user1.setAddress("42 Main St");
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setInvoiceData(invoiceData);
        user1.setLastName("Doe");
        user1.setOrders(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setCustomer(user1);
        shoppingCart.setId(123L);
        shoppingCart.setTotalItems(1000);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(42L));

        CartItem cartItem = new CartItem();
        cartItem.setId(123L);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setTotalPrice(BigDecimal.valueOf(42L));
        doNothing().when(cartItemRepository).deleteById((Long) any());
        when(cartItemRepository.findByIdAndShoppingCart_Id((Long) any(), (Long) any())).thenReturn(cartItem);

        InvoiceData invoiceData1 = new InvoiceData();
        invoiceData1.setCompanyName("Company Name");
        invoiceData1.setCustomer(new User());
        invoiceData1.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData1.setId(123L);
        invoiceData1.setIdentificationNumberUIC("42");
        invoiceData1.setPhoneNumber("4105551212");
        invoiceData1.setRegisteredAddress("42 Main St");
        invoiceData1.setVatRegistration(true);

        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setInvoiceData(invoiceData1);
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

        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setCartItems(new HashSet<>());
        shoppingCart1.setCustomer(user3);
        shoppingCart1.setId(123L);
        shoppingCart1.setTotalItems(1000);
        shoppingCart1.setTotalPrice(BigDecimal.valueOf(42L));
        when(shoppingCartRepository.findShoppingCartByCustomer_Id((Long) any())).thenReturn(shoppingCart1);

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

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setCartItems(new HashSet<>());
        shoppingCart2.setCustomer(user5);
        shoppingCart2.setId(123L);
        shoppingCart2.setTotalItems(1000);
        shoppingCart2.setTotalPrice(BigDecimal.valueOf(42L));
        when(entityManager.merge((ShoppingCart) any())).thenReturn(shoppingCart2);
        doNothing().when(entityManager).detach((Object) any());

        InvoiceData invoiceData5 = new InvoiceData();
        invoiceData5.setCompanyName("Company Name");
        invoiceData5.setCustomer(new User());
        invoiceData5.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData5.setId(123L);
        invoiceData5.setIdentificationNumberUIC("42");
        invoiceData5.setPhoneNumber("4105551212");
        invoiceData5.setRegisteredAddress("42 Main St");
        invoiceData5.setVatRegistration(true);

        User user6 = new User();
        user6.setAddress("42 Main St");
        user6.setCity("Oxford");
        user6.setEmail("jane.doe@example.org");
        user6.setFirstName("Jane");
        user6.setId(123L);
        user6.setInvoiceData(invoiceData5);
        user6.setLastName("Doe");
        user6.setOrders(new HashSet<>());
        user6.setPassword("iloveyou");
        user6.setPhoneNumber("4105551212");
        user6.setRoles(new ArrayList<>());
        user6.setUsername("janedoe");

        InvoiceData invoiceData6 = new InvoiceData();
        invoiceData6.setCompanyName("Company Name");
        invoiceData6.setCustomer(user6);
        invoiceData6.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData6.setId(123L);
        invoiceData6.setIdentificationNumberUIC("42");
        invoiceData6.setPhoneNumber("4105551212");
        invoiceData6.setRegisteredAddress("42 Main St");
        invoiceData6.setVatRegistration(true);

        User user7 = new User();
        user7.setAddress("42 Main St");
        user7.setCity("Oxford");
        user7.setEmail("jane.doe@example.org");
        user7.setFirstName("Jane");
        user7.setId(123L);
        user7.setInvoiceData(invoiceData6);
        user7.setLastName("Doe");
        user7.setOrders(new HashSet<>());
        user7.setPassword("iloveyou");
        user7.setPhoneNumber("4105551212");
        user7.setRoles(new ArrayList<>());
        user7.setUsername("janedoe");
        ShoppingCart actualDeleteItemFromCartResult = shoppingCartServiceImpl.deleteItemFromCart(123L, user7);
        assertSame(shoppingCart2, actualDeleteItemFromCartResult);
        assertEquals("42", actualDeleteItemFromCartResult.getTotalPrice().toString());
        verify(cartItemRepository).findByIdAndShoppingCart_Id((Long) any(), (Long) any());
        verify(cartItemRepository).deleteById((Long) any());
        verify(shoppingCartRepository).findShoppingCartByCustomer_Id((Long) any());
        verify(entityManager).merge((ShoppingCart) any());
        verify(entityManager).detach((Object) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#deleteItemFromCart(Long, User)}
     */
    @Test
    void testDeleteItemFromCart2() {
        Category category = new Category();
        category.setId(123L);
        category.setName("Name");

        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setImgUrl("https://example.org/example");
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));

        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setInvoiceData(new InvoiceData());
        user.setLastName("Doe");
        user.setOrders(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setCompanyName("Company Name");
        invoiceData.setCustomer(user);
        invoiceData.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData.setId(123L);
        invoiceData.setIdentificationNumberUIC("42");
        invoiceData.setPhoneNumber("4105551212");
        invoiceData.setRegisteredAddress("42 Main St");
        invoiceData.setVatRegistration(true);

        User user1 = new User();
        user1.setAddress("42 Main St");
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setInvoiceData(invoiceData);
        user1.setLastName("Doe");
        user1.setOrders(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setCustomer(user1);
        shoppingCart.setId(123L);
        shoppingCart.setTotalItems(1000);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(42L));

        CartItem cartItem = new CartItem();
        cartItem.setId(123L);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setTotalPrice(BigDecimal.valueOf(42L));
        doNothing().when(cartItemRepository).deleteById((Long) any());
        when(cartItemRepository.findByIdAndShoppingCart_Id((Long) any(), (Long) any())).thenReturn(cartItem);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");

        Product product1 = new Product();
        product1.setBrand("Brand");
        product1.setCategory(category1);
        product1.setDescription("The characteristics of someone or something");
        product1.setId(123L);
        product1.setImgUrl("https://example.org/example");
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));

        InvoiceData invoiceData1 = new InvoiceData();
        invoiceData1.setCompanyName("Company Name");
        invoiceData1.setCustomer(new User());
        invoiceData1.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData1.setId(123L);
        invoiceData1.setIdentificationNumberUIC("42");
        invoiceData1.setPhoneNumber("4105551212");
        invoiceData1.setRegisteredAddress("42 Main St");
        invoiceData1.setVatRegistration(true);

        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setInvoiceData(invoiceData1);
        user2.setLastName("Doe");
        user2.setOrders(new HashSet<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("4105551212");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setCartItems(new HashSet<>());
        shoppingCart1.setCustomer(user2);
        shoppingCart1.setId(123L);
        shoppingCart1.setTotalItems(1000);
        shoppingCart1.setTotalPrice(BigDecimal.valueOf(42L));

        CartItem cartItem1 = new CartItem();
        cartItem1.setId(123L);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(1);
        cartItem1.setShoppingCart(shoppingCart1);
        cartItem1.setTotalPrice(BigDecimal.valueOf(42L));

        HashSet<CartItem> cartItemSet = new HashSet<>();
        cartItemSet.add(cartItem1);

        InvoiceData invoiceData2 = new InvoiceData();
        invoiceData2.setCompanyName("Company Name");
        invoiceData2.setCustomer(new User());
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

        InvoiceData invoiceData3 = new InvoiceData();
        invoiceData3.setCompanyName("Company Name");
        invoiceData3.setCustomer(user3);
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

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setCartItems(cartItemSet);
        shoppingCart2.setCustomer(user4);
        shoppingCart2.setId(123L);
        shoppingCart2.setTotalItems(1000);
        shoppingCart2.setTotalPrice(BigDecimal.valueOf(42L));
        when(shoppingCartRepository.findShoppingCartByCustomer_Id((Long) any())).thenReturn(shoppingCart2);

        InvoiceData invoiceData4 = new InvoiceData();
        invoiceData4.setCompanyName("Company Name");
        invoiceData4.setCustomer(new User());
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

        InvoiceData invoiceData5 = new InvoiceData();
        invoiceData5.setCompanyName("Company Name");
        invoiceData5.setCustomer(user5);
        invoiceData5.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData5.setId(123L);
        invoiceData5.setIdentificationNumberUIC("42");
        invoiceData5.setPhoneNumber("4105551212");
        invoiceData5.setRegisteredAddress("42 Main St");
        invoiceData5.setVatRegistration(true);

        User user6 = new User();
        user6.setAddress("42 Main St");
        user6.setCity("Oxford");
        user6.setEmail("jane.doe@example.org");
        user6.setFirstName("Jane");
        user6.setId(123L);
        user6.setInvoiceData(invoiceData5);
        user6.setLastName("Doe");
        user6.setOrders(new HashSet<>());
        user6.setPassword("iloveyou");
        user6.setPhoneNumber("4105551212");
        user6.setRoles(new ArrayList<>());
        user6.setUsername("janedoe");

        ShoppingCart shoppingCart3 = new ShoppingCart();
        shoppingCart3.setCartItems(new HashSet<>());
        shoppingCart3.setCustomer(user6);
        shoppingCart3.setId(123L);
        shoppingCart3.setTotalItems(1000);
        shoppingCart3.setTotalPrice(BigDecimal.valueOf(42L));
        when(entityManager.merge((ShoppingCart) any())).thenReturn(shoppingCart3);
        doNothing().when(entityManager).detach((Object) any());

        InvoiceData invoiceData6 = new InvoiceData();
        invoiceData6.setCompanyName("Company Name");
        invoiceData6.setCustomer(new User());
        invoiceData6.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData6.setId(123L);
        invoiceData6.setIdentificationNumberUIC("42");
        invoiceData6.setPhoneNumber("4105551212");
        invoiceData6.setRegisteredAddress("42 Main St");
        invoiceData6.setVatRegistration(true);

        User user7 = new User();
        user7.setAddress("42 Main St");
        user7.setCity("Oxford");
        user7.setEmail("jane.doe@example.org");
        user7.setFirstName("Jane");
        user7.setId(123L);
        user7.setInvoiceData(invoiceData6);
        user7.setLastName("Doe");
        user7.setOrders(new HashSet<>());
        user7.setPassword("iloveyou");
        user7.setPhoneNumber("4105551212");
        user7.setRoles(new ArrayList<>());
        user7.setUsername("janedoe");

        InvoiceData invoiceData7 = new InvoiceData();
        invoiceData7.setCompanyName("Company Name");
        invoiceData7.setCustomer(user7);
        invoiceData7.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData7.setId(123L);
        invoiceData7.setIdentificationNumberUIC("42");
        invoiceData7.setPhoneNumber("4105551212");
        invoiceData7.setRegisteredAddress("42 Main St");
        invoiceData7.setVatRegistration(true);

        User user8 = new User();
        user8.setAddress("42 Main St");
        user8.setCity("Oxford");
        user8.setEmail("jane.doe@example.org");
        user8.setFirstName("Jane");
        user8.setId(123L);
        user8.setInvoiceData(invoiceData7);
        user8.setLastName("Doe");
        user8.setOrders(new HashSet<>());
        user8.setPassword("iloveyou");
        user8.setPhoneNumber("4105551212");
        user8.setRoles(new ArrayList<>());
        user8.setUsername("janedoe");
        ShoppingCart actualDeleteItemFromCartResult = shoppingCartServiceImpl.deleteItemFromCart(123L, user8);
        assertSame(shoppingCart3, actualDeleteItemFromCartResult);
        assertEquals("42", actualDeleteItemFromCartResult.getTotalPrice().toString());
        verify(cartItemRepository).findByIdAndShoppingCart_Id((Long) any(), (Long) any());
        verify(cartItemRepository).deleteById((Long) any());
        verify(shoppingCartRepository).findShoppingCartByCustomer_Id((Long) any());
        verify(entityManager).merge((ShoppingCart) any());
        verify(entityManager).detach((Object) any());
    }
}

