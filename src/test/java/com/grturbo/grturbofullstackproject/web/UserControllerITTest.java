package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.TestData;
import com.grturbo.grturbofullstackproject.model.dto.UserUpdateDto;
import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.impl.InvoiceDataServiceImpl;
import com.grturbo.grturbofullstackproject.service.impl.UserServiceImpl;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private InvoiceDataServiceImpl invoiceDataService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserController userController;

    @Autowired
    private TestData testData;

    @Test
    @WithMockUser( username = "test@example.com")
    void testProfileViewWithPrincipal() throws Exception {
        User principal = testData.createUser("test@example.com");

        User user = new User();
        user.setAddress("Luben Karavelov 3");
        user.setCity("Razlog");
        user.setEmail("test@example.com");
        user.setFirstName("Hristina");
        user.setId(123L);
        user.setInvoiceData(new InvoiceData());
        user.setLastName("Racheva");
        user.setOrders(new HashSet<>());
        user.setPassword("topsecret");
        user.setPhoneNumber("1234567890");
        user.setRoles(new ArrayList<>());
        user.setUsername("hristina");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/profile")
                        .principal(() -> "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("page"))
                .andExpect(view().name("user-profile"))
                .andReturn();
    }

    @Test
    void testProfileViewWithoutPrincipal() throws Exception {
        String baseUrl = "http://localhost";
        mockMvc.perform(MockMvcRequestBuilders.get("/profile")
                        .principal(() -> null))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(baseUrl + "/login"));
    }

    @Test
    void testUpdateProfileSuccessfully() throws Exception {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setAddress("42 Main St");
        userUpdateDto.setCity("Oxford");
        userUpdateDto.setConfirmPassword("iloveyou");
        userUpdateDto.setEmail("jane.doe@example.org");
        userUpdateDto.setFirstName("Jane");
        userUpdateDto.setLastName("Doe");
        userUpdateDto.setPassword("iloveyou");
        userUpdateDto.setPhoneNumber("4105551212");
        userUpdateDto.setUsername("janedoe");

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

        InvoiceData invoiceData1 = new InvoiceData();
        invoiceData1.setCompanyName("Company Name");
        invoiceData1.setCustomer(user1);
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
        when(userService.getUser(any())).thenReturn(userUpdateDto);
        when(userService.update(any())).thenReturn(user2);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/update-profile");
        postResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
    }

    @Test
    void testUpdateProfileWithoutPrincipal() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/update-profile");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testProfileInvoiceViewSuccessfully() throws Exception {
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
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/profile-invoice");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("invoiceData"))
                .andExpect(MockMvcResultMatchers.view().name("user-invoice-data"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user-invoice-data"));
    }

    @Test
    void testProfileInvoiceViewWithoutPrincipal() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/profile-invoice");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testUpdateInvoiceData() throws Exception {
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
        doNothing().when(invoiceDataService).saveInvoiceData(any(), any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/update-profile-invoice");
        postResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("invoiceData", "success"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile-invoice"))
                .andExpect(MockMvcResultMatchers
                        .redirectedUrl("/profile-invoice?success=Invoice+information+updated+successfully%21"));
    }

    @Test
    void testUpdateInvoiceDataWithoutPrincipal() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/update-profile-invoice");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("invoiceData"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser( username = "test@example.com")
    void testChangePasswordView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/change-password")
                        .principal(() -> "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("page"))
                .andExpect(view().name("change-password"))
                .andReturn();
    }

    @Test
    void testChangePasswordViewWithoutPrincipal() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/change-password");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }









}
