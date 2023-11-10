package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.dto.ProductDetailDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductRecentDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.service.CategoryService;
import com.grturbo.grturbofullstackproject.service.ProductService;
import com.grturbo.grturbofullstackproject.service.impl.CategoryServiceImpl;
import com.grturbo.grturbofullstackproject.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShopControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "http://localhost";

    @MockBean
    private CategoryServiceImpl categoryService;

    @MockBean
    private ProductServiceImpl productService;

    @Test
    void testShop() throws Exception {
        List<ProductRecentDto> mockRecentProducts = new ArrayList<>();
        when(productService.findRecentProducts(10)).thenReturn(mockRecentProducts);

        List<Category> mockCategories = new ArrayList<>();
        when(categoryService.getAllCategory()).thenReturn(mockCategories);

        Pageable pageable = Pageable.unpaged(); // You can customize pageable as needed
        Page<ProductDetailDto> mockProducts = new PageImpl<>(new ArrayList<>());// Add your mocked data
        when(productService.getAllProducts(pageable)).thenReturn(mockProducts);

        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/shop");
        mockMvc.perform(getRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("recentProducts"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"));
    }
}
