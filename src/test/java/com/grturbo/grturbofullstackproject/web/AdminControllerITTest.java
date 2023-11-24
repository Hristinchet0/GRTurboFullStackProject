package com.grturbo.grturbofullstackproject.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.grturbo.grturbofullstackproject.model.dto.CategoryDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductAddDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductEditDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.Order;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.enums.OrderStatusEnum;
import com.grturbo.grturbofullstackproject.model.enums.PaymentMethodEnum;
import com.grturbo.grturbofullstackproject.service.CategoryService;
import com.grturbo.grturbofullstackproject.service.OrderService;
import com.grturbo.grturbofullstackproject.service.ProductService;
import com.grturbo.grturbofullstackproject.service.RoleService;
import com.grturbo.grturbofullstackproject.service.UserService;
import com.sun.security.auth.UserPrincipal;

import java.math.BigDecimal;

import java.security.Principal;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
class AdminControllerITTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserService userService;

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/user");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.view().name("admin-user-all"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-user-all"));
    }

    @Test
    void testSaveUser() throws Exception {
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
        when(userService.save((User) any())).thenReturn(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/user/save");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/user"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/user"));
    }

    @Test
    void testAcceptOrder() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/admin/accept-order");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testAcceptOrder3() throws Exception {
        doNothing().when(orderService).acceptOrder((Long) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/admin/accept-order");
        postResult.principal(new UserPrincipal("principal"));
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/user-orders"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/user-orders"));
    }

    /**
     * Method under test: {@link AdminController#acceptOrder(Long, RedirectAttributes, Principal)}
     */
    @Test
    void testAcceptOrder2() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/admin/accept-order", "Uri Vars");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testAddProductPage() throws Exception {
        when(categoryService.getAllCategory()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/add-product");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories", "productDto", "title"))
                .andExpect(MockMvcResultMatchers.view().name("admin-add-product"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-add-product"));
    }

    @Test
    void testAdminHome() throws Exception {
        when(orderService.getSentOrdersForCurrentMonth()).thenReturn(1L);
        when(orderService.getSentOrdersForCurrentYear()).thenReturn(1L);
        when(orderService.calculateAnnualEarnings()).thenReturn(BigDecimal.valueOf(42L));
        when(orderService.calculateTotalPriceForLastMonth()).thenReturn(BigDecimal.valueOf(42L));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/home");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("annualEarnings", "monthlyEarnings", "sentOrdersCount", "sentOrdersCountForYear"))
                .andExpect(MockMvcResultMatchers.view().name("admin-index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-index"));
    }

    @Test
    void testAllProducts() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/products/{pageNo}", 1);
        MockHttpServletRequestBuilder requestBuilder = getResult.param("pageSize", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testAllProducts2() throws Exception {
        when(productService.getAllProducts(anyInt(), anyInt())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/products/{pageNo}", 1);
        getResult.principal(new UserPrincipal("principal"));
        MockHttpServletRequestBuilder requestBuilder = getResult.param("pageSize", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("currentPage", "products", "size", "title", "totalPages"))
                .andExpect(MockMvcResultMatchers.view().name("admin-products"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-products"));
    }

    @Test
    void testCategories() throws Exception {
        when(categoryService.getAllCategory()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/categories");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories", "categoryNew", "size", "title"))
                .andExpect(MockMvcResultMatchers.view().name("admin-categories"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-categories"));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(categoryService).removeCategoryById((Long) any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/delete-category");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    @Test
    void testDelete2() throws Exception {
        doThrow(new DataIntegrityViolationException("?")).when(categoryService).removeCategoryById((Long) any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/delete-category");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).removeUserById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/user/delete/{id}", 123L);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/user"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/user"));
    }

    @Test
    void testDeletedProduct() throws Exception {
        doNothing().when(productService).removeProductById((Long) any());
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/admin/delete-product");
        MockHttpServletRequestBuilder requestBuilder = putResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/products/0"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/products/0"));
    }

    @Test
    void testDeletedProduct2() throws Exception {
        doThrow(new DataIntegrityViolationException("?")).when(productService).removeProductById((Long) any());
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/admin/delete-product");
        MockHttpServletRequestBuilder requestBuilder = putResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/products/0"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/products/0"));
    }

    @Test
    void testFindById() throws Exception {
        Category category = new Category();
        category.setId(123L);
        category.setName("Name");
        Optional<Category> ofResult = Optional.of(category);
        when(categoryService.findCategoryById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/admin/findById");
        MockHttpServletRequestBuilder requestBuilder = putResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"name\":\"Name\"}"));
    }

    @Test
    void testGetAll() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/user-orders");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testGetAll2() throws Exception {
        when(orderService.getOrdersWithDetails()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/user-orders");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orders"))
                .andExpect(MockMvcResultMatchers.view().name("admin-user-orders"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-user-orders"));
    }

    @Test
    void testProducts() throws Exception {
        when(productService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/products");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products", "size"))
                .andExpect(MockMvcResultMatchers.view().name("admin-products"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-products"));
    }

    @Test
    void testSave() throws Exception {
        doNothing().when(categoryService).addCategory((CategoryDto) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/save-category");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    @Test
    void testSave2() throws Exception {
        doThrow(new DataIntegrityViolationException("?")).when(categoryService).addCategory((CategoryDto) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/save-category");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    @Test
    void testSave3() throws Exception {
        doNothing().when(categoryService).addCategory((CategoryDto) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/admin/save-category");
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    /**
     * Method under test: {@link AdminController#saveProduct(ProductAddDto, RedirectAttributes)}
     */
    @Test
    void testSaveProduct() throws Exception {
        doNothing().when(productService).addProduct((ProductAddDto) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/save-product");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/products/0"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/products/0"));
    }

    @Test
    void testSaveProduct2() throws Exception {
        doThrow(new DataIntegrityViolationException("?")).when(productService).addProduct((ProductAddDto) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/save-product");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/products/0"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/products/0"));
    }

    @Test
    void testSaveProduct3() throws Exception {
        doNothing().when(productService).addProduct((ProductAddDto) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/admin/save-product");
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/products/0"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/products/0"));
    }

    @Test
    void testSearchProduct() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/search-products/{pageNo}", 1)
                .param("keyword", "foo");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testSearchProduct2() throws Exception {
        when(productService.searchProducts(anyInt(), (String) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/search-products/{pageNo}", 1);
        getResult.principal(new UserPrincipal("principal"));
        MockHttpServletRequestBuilder requestBuilder = getResult.param("keyword", "foo");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("currentPage", "products", "size", "title", "totalPages"))
                .andExpect(MockMvcResultMatchers.view().name("admin-products"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-products"));
    }

    @Test
    void testShippingConfirmation() throws Exception {
        doNothing().when(orderService).sendOrder((Long) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/admin/send-order");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/user-orders"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/user-orders"));
    }

    @Test
    void testUpdate() throws Exception {
        doNothing().when(categoryService).editCategory((Category) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/update-category");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    @Test
    void testUpdate2() throws Exception {
        doThrow(new DataIntegrityViolationException("?")).when(categoryService).editCategory((Category) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/update-category");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    @Test
    void testUpdate3() throws Exception {
        doNothing().when(categoryService).editCategory((Category) any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/update-category");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        doNothing().when(productService).saveProduct((ProductEditDto) any(), (Product) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/update-product/{id}", 123L);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/products/0"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/products/0"));
    }

    @Test
    void testUpdateProduct3() throws Exception {
        doNothing().when(productService).saveProduct((ProductEditDto) any(), (Product) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/admin/update-product/{id}", 123L);
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/products/0"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/products/0"));
    }

    @Test
    void testUpdateProductForm() throws Exception {
        when(categoryService.getAllCategory()).thenReturn(new ArrayList<>());

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
        Optional<Product> ofResult = Optional.of(product);
        when(productService.getProductById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/update-product/{id}", 123L);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories", "productDto", "title"))
                .andExpect(MockMvcResultMatchers.view().name("admin-update-product"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-update-product"));
    }

    @Test
    void testUpdateUser() throws Exception {
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
        when(userService.getUserById((Long) any())).thenReturn(ofResult);
        when(roleService.getAllRoles()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/user/update/{id}", 123L);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("roles", "user"))
                .andExpect(MockMvcResultMatchers.view().name("admin-user-update"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-user-update"));
    }

    @Test
    void testViewOrderDetail() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/view-order-detail");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("orderId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testViewOrderDetail2() throws Exception {
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
        when(orderService.getOrderWithDetails((Long) any())).thenReturn(order);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/view-order-detail");
        getResult.principal(new UserPrincipal("principal"));
        MockHttpServletRequestBuilder requestBuilder = getResult.param("orderId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("order"))
                .andExpect(MockMvcResultMatchers.view().name("admin-user-order-details"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin-user-order-details"));
    }

    @Test
    void testViewOrderDetailPost() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/admin/view-order-detail");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("orderId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/view-order-detail?orderId=1"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/view-order-detail?orderId=1"));
    }
}

