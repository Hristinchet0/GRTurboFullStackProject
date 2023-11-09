package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.CustomUserDetail;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.grturbo.grturbofullstackproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "http://localhost";

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private UserServiceImpl userService;

    @Test
    @Disabled("principal error")
    void testHomeWithPrincipal() throws Exception {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setUsername("testUser");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        CustomUserDetail userDetail = new CustomUserDetail(1L, "topsecret", "test@example.com", "Hristina", "Racheva", authorities);

        ShoppingCart mockCart = new ShoppingCart();
        mockCart.setId(1L);
        mockCart.setCustomer(mockUser);
        mockCart.setTotalItems(5);

        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(shoppingCartService.findByUserId(1L)).thenReturn(mockCart);

        mockMvc.perform(MockMvcRequestBuilders.get("/index")
                        .principal(userDetail::getUsername))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("totalItems", 5));
    }

    @Test
    void testHomeControllerWithoutPrincipal() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeDoesNotExist("totalItems"));

    }

    @Test
    void testAboutPageShown() throws Exception {
        mockMvc.perform(get("/about")).
                andExpect(status().isOk()).
                andExpect(view().name("about"));
    }

    @Test
    void testContactPageShown() throws Exception {
        mockMvc.perform(get("/contactus")).
                andExpect(status().isOk()).
                andExpect(view().name("contactus"));
    }

    @Test
    void testFaqPageShown() throws Exception {
        mockMvc.perform(get("/faq")).
                andExpect(status().isOk()).
                andExpect(view().name("faq"));
    }





}

