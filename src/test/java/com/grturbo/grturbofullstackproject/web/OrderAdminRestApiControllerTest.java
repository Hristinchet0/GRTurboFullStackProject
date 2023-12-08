package com.grturbo.grturbofullstackproject.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

import com.grturbo.grturbofullstackproject.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderAdminRestApiController.class})
@ExtendWith(SpringExtension.class)
class OrderAdminRestApiControllerTest {

    @Autowired
    private OrderAdminRestApiController orderAdminRestApiController;

    @MockBean
    private OrderService orderService;

    @Test
    void testCancelOrder() throws Exception {
        doNothing().when(orderService).cancelOrder((Long) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/admin/cancel-order");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(orderAdminRestApiController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("\"OK\""));
    }
}

