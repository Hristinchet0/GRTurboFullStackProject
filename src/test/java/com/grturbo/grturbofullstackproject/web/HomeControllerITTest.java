package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.TestData;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;

import java.security.Principal;
import java.util.Optional;

import com.grturbo.grturbofullstackproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HomeControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private TestData testData;

    @Test
    @WithMockUser( username = "test@example.com")
    void testHomeWithPrincipal() throws Exception {

        User user = testData.createUser("test@example.com");
        ShoppingCart shoppingCart = testData.createShoppingCart(user, 5);

        ShoppingCart byUserId = shoppingCartService.findByUserId(1L);
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(byUserId).thenReturn(shoppingCart);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/index")
                        .principal(() -> "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andReturn();

        HttpSession mockSession = mvcResult.getRequest().getSession();

        assert mockSession != null;
        Assertions.assertEquals(5, mockSession.getAttribute("totalItems"));
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

