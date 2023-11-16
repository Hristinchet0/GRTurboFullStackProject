package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.TestData;
import com.grturbo.grturbofullstackproject.model.dto.ProductDetailDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductRecentDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.service.impl.CategoryServiceImpl;
import com.grturbo.grturbofullstackproject.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShopControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    private final String baseUrl = "http://localhost";

    @MockBean
    private CategoryServiceImpl categoryService;

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private TestData testData;

    @BeforeEach
    public void setup() {
        testData.createCategoriesAndProducts();
    }

    @Test
    void testShop() throws Exception {
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("testCategory");

        ProductRecentDto mockProduct = new ProductRecentDto();
        mockProduct.setName("Sample Product");
        mockProduct.setBrand("testBrand");
        mockProduct.setPrice(BigDecimal.TEN);

        List<ProductRecentDto> mockRecentProducts = new ArrayList<>();
        mockRecentProducts.add(mockProduct);
        when(productService.findRecentProducts(10)).thenReturn(mockRecentProducts);

        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(mockCategory);
        when(categoryService.getAllCategory()).thenReturn(mockCategories);

        Pageable pageable = Pageable.ofSize(10);
        Page<ProductDetailDto> mockProducts = new PageImpl<>(new ArrayList<>());
        when(productService.getAllProducts(pageable)).thenReturn(mockProducts);

        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/shop");
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recentProducts"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    public void testShopByCategory() throws Exception {
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("testCategory");

        ProductRecentDto mockProduct = new ProductRecentDto();
        mockProduct.setName("Sample Product");
        mockProduct.setBrand("testBrand");
        mockProduct.setPrice(BigDecimal.TEN);

        List<ProductRecentDto> mockRecentProducts = new ArrayList<>();
        mockRecentProducts.add(mockProduct);
        List<ProductRecentDto> recentProducts = productService.findRecentProducts(10);
        when(recentProducts).thenReturn(mockRecentProducts);

        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(mockCategory);
        when(categoryService.getAllCategory()).thenReturn(mockCategories);

        Pageable pageable = Pageable.ofSize(10);
        Page<ProductDetailDto> mockProducts = new PageImpl<>(new ArrayList<>());
        when(productService.getAllProductsByCategoryId(mockCategory.getId() ,pageable)).thenReturn(mockProducts);

        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/shop/category/1");
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recentProducts"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    public void testViewProduct() throws Exception {
        Product mockProduct = new Product();
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("testCategory");
        mockProduct.setId(1L);
        mockProduct.setName("Sample Product");
        mockProduct.setCategory(mockCategory);

        when(productService.getProductById(1L)).thenReturn(Optional.of(mockProduct));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/viewproduct/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", mockProduct))
                .andExpect(model().attributeExists("success"))
                .andExpect(model().attribute("success", "Add order successfully"));
    }

    @Test
    public void testSearchProduct() throws Exception {
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("testCategory");

        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(mockCategory);
        when(categoryService.getAllCategory()).thenReturn(mockCategories);

        mockMvc.perform(MockMvcRequestBuilders.get("/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("shop-search"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("noResultsMessage"))
                .andExpect(model().attribute("noResultsMessage", "No results found"));
    }

    @Test
    public void testSearchProducts() throws Exception {
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("testCategory");

        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(mockCategory);
        when(categoryService.getAllCategory()).thenReturn(mockCategories);

        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setCategory(mockCategory);
        mockProduct.setName("testProduct");
        mockProduct.setBrand("testBrand");
        mockProduct.setImgUrl("testImgUrl");
        mockProduct.setPrice(BigDecimal.TEN);
        mockProduct.setDescription("testDescription");

        List<Product> mockSearchResults = new ArrayList<>();
        mockSearchResults.add(mockProduct);
        when(productService.searchProducts(anyString())).thenReturn(mockSearchResults);

        mockMvc.perform(post("/search")
                        .param("query", "testProduct")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("shop-search"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("categories", mockCategories))
                .andExpect(model().attribute("products", mockSearchResults))
                .andExpect(model().attributeDoesNotExist("noResultsMessage"));
    }

    @Test
    public void testSearchProductsWithNoResults() throws Exception {
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("testCategory");

        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(mockCategory);
        when(categoryService.getAllCategory()).thenReturn(mockCategories);

        mockMvc.perform(post("/search")
                        .param("query", "testProduct")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("shop-search"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", mockCategories))
                .andExpect(model().attributeExists("noResultsMessage"));
    }
}
