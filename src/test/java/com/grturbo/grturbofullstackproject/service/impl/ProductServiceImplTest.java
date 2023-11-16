package com.grturbo.grturbofullstackproject.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.grturbo.grturbofullstackproject.model.dto.ProductAddDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductDetailDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductEditDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.repositority.ProductRepository;
import com.grturbo.grturbofullstackproject.service.CategoryService;
import com.grturbo.grturbofullstackproject.service.CloudinaryService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CloudinaryService cloudinaryService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Test
    void testFindAll() {
        assertTrue(productServiceImpl.findAll().isEmpty());
    }

    @Test
    void testAddProduct() throws IOException {
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
        when(productRepository.save(any())).thenReturn(product);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        Optional<Category> ofResult = Optional.of(category1);
        when(categoryService.findCategoryById(any())).thenReturn(ofResult);
        when(cloudinaryService.uploadImage(any())).thenReturn("Upload Image");

        ProductAddDto productAddDto = new ProductAddDto();
        productAddDto.setBrand("Brand");
        productAddDto.setCategoryId(123L);
        productAddDto.setDescription("The characteristics of someone or something");
        productAddDto.setId("42");
        productAddDto.setImg(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes(StandardCharsets.UTF_8))));
        productAddDto.setName("Name");
        productAddDto.setPrice(BigDecimal.valueOf(42L));
        productServiceImpl.addProduct(productAddDto);
        verify(productRepository).save(any());
        verify(categoryService).findCategoryById(any());
        verify(cloudinaryService).uploadImage(any());
    }

    @Test
    void testRemoveProductById() {
        doNothing().when(productRepository).deleteById(any());
        productServiceImpl.removeProductById(123L);
        verify(productRepository).deleteById(any());
    }

    @Test
    void testGetProductByID() {
        assertNull(productServiceImpl.getProductByID(123L));
    }

    @Test
    void testGetProductById() {
        assertFalse(productServiceImpl.getProductById(123L).isPresent());
        assertFalse(productServiceImpl.getProductById(1L).isPresent());
    }

    @Test
    void testSaveProduct() throws IOException {
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
        when(productRepository.save((Product) any())).thenReturn(product);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        Optional<Category> ofResult = Optional.of(category1);
        when(categoryService.findCategoryById(any())).thenReturn(ofResult);
        when(cloudinaryService.uploadImage(any())).thenReturn("Upload Image");

        ProductEditDto productEditDto = new ProductEditDto();
        productEditDto.setBrand("Brand");
        productEditDto.setCategoryId(123L);
        productEditDto.setDescription("The characteristics of someone or something");
        productEditDto.setId(123L);
        productEditDto.setImg(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes(StandardCharsets.UTF_8))));
        productEditDto.setName("Name");
        productEditDto.setPrice(BigDecimal.valueOf(42L));

        Category category2 = new Category();
        category2.setId(123L);
        category2.setName("Name");

        Product product1 = new Product();
        product1.setBrand("Brand");
        product1.setCategory(category2);
        product1.setDescription("The characteristics of someone or something");
        product1.setId(123L);
        product1.setImgUrl("https://example.org/example");
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        productServiceImpl.saveProduct(productEditDto, product1);
        verify(productRepository).save(any());
        verify(categoryService).findCategoryById(any());
        verify(cloudinaryService).uploadImage(any());
        assertEquals("Brand", product1.getBrand());
        BigDecimal expectedPrice = productEditDto.getPrice();
        assertSame(expectedPrice, product1.getPrice());
        assertEquals("Name", product1.getName());
        assertEquals("Upload Image", product1.getImgUrl());
        assertEquals("The characteristics of someone or something", product1.getDescription());
        assertSame(category1, product1.getCategory());
    }

    @Test
    void testSaveProduct3() throws IOException {
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
        when(productRepository.save(any())).thenReturn(product);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        Optional<Category> ofResult = Optional.of(category1);
        when(categoryService.findCategoryById(any())).thenReturn(ofResult);
        when(cloudinaryService.uploadImage(any())).thenReturn("Upload Image");
        ProductEditDto productEditDto = mock(ProductEditDto.class);
        when(productEditDto.getCategoryId()).thenReturn(123L);
        when(productEditDto.getBrand()).thenReturn("Brand");
        when(productEditDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(productEditDto.getName()).thenReturn("Name");
        when(productEditDto.getPrice()).thenReturn(BigDecimal.valueOf(42L));
        when(productEditDto.getImg()).thenReturn(new MockMultipartFile("Name", new ByteArrayInputStream(new byte[]{})));
        doNothing().when(productEditDto).setBrand(any());
        doNothing().when(productEditDto).setCategoryId(any());
        doNothing().when(productEditDto).setDescription(any());
        doNothing().when(productEditDto).setId(any());
        doNothing().when(productEditDto).setImg(any());
        doNothing().when(productEditDto).setName(any());
        doNothing().when(productEditDto).setPrice(any());
        productEditDto.setBrand("Brand");
        productEditDto.setCategoryId(123L);
        productEditDto.setDescription("The characteristics of someone or something");
        productEditDto.setId(123L);
        productEditDto.setImg(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes(StandardCharsets.UTF_8))));
        productEditDto.setName("Name");
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        productEditDto.setPrice(valueOfResult);

        Category category2 = new Category();
        category2.setId(123L);
        category2.setName("Name");

        Product product1 = new Product();
        product1.setBrand("Brand");
        product1.setCategory(category2);
        product1.setDescription("The characteristics of someone or something");
        product1.setId(123L);
        product1.setImgUrl("https://example.org/example");
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        productServiceImpl.saveProduct(productEditDto, product1);
        verify(productRepository).save(any());
        verify(categoryService).findCategoryById(any());
        verify(productEditDto).getCategoryId();
        verify(productEditDto).getBrand();
        verify(productEditDto).getDescription();
        verify(productEditDto).getName();
        verify(productEditDto).getPrice();
        verify(productEditDto).getImg();
        verify(productEditDto).setBrand(any());
        verify(productEditDto).setCategoryId(any());
        verify(productEditDto).setDescription(any());
        verify(productEditDto).setId(any());
        verify(productEditDto).setImg(any());
        verify(productEditDto).setName(any());
        verify(productEditDto).setPrice(any());
        assertEquals("Brand", product1.getBrand());
        assertEquals(valueOfResult, product1.getPrice());
        assertEquals("Name", product1.getName());
        assertSame(category1, product1.getCategory());
        assertEquals("https://example.org/example", product1.getImgUrl());
        assertEquals("The characteristics of someone or something", product1.getDescription());
    }

    @Test
    void testGetAllProducts() {
        assertTrue(productServiceImpl.getAllProducts(1, 3).toList().isEmpty());
        assertTrue(productServiceImpl.getAllProducts(0, 3).toList().isEmpty());
    }

    @Test
    void testGetAllProducts3() {
        when(productRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(productServiceImpl.getAllProducts(null).toList().isEmpty());
        verify(productRepository).findAll((Pageable) any());
    }

    @Test
    void testGetAllProducts4() {
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

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        PageImpl<Product> pageImpl = new PageImpl<>(productList);
        when(productRepository.findAll((Pageable) any())).thenReturn(pageImpl);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        Optional<Category> ofResult = Optional.of(category1);
        when(categoryService.findById(any())).thenReturn(ofResult);

        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setBrand("Brand");
        productDetailDto.setCategory("Category");
        productDetailDto.setDescription("The characteristics of someone or something");
        productDetailDto.setId(123L);
        productDetailDto.setImgUrl("https://example.org/example");
        productDetailDto.setName("Name");
        productDetailDto.setPrice(BigDecimal.valueOf(42L));
        when(modelMapper.map(any(), (Class<ProductDetailDto>) any())).thenReturn(productDetailDto);
        assertEquals(1, productServiceImpl.getAllProducts(null).toList().size());
        verify(productRepository).findAll((Pageable) any());
        verify(categoryService).findById(any());
        verify(modelMapper).map(any(), (Class<ProductDetailDto>) any());
    }

    @Test
    void testGetAllProducts5() {
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

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product);
        PageImpl<Product> pageImpl = new PageImpl<>(productList);
        when(productRepository.findAll((Pageable) any())).thenReturn(pageImpl);

        Category category2 = new Category();
        category2.setId(123L);
        category2.setName("Name");
        Optional<Category> ofResult = Optional.of(category2);
        when(categoryService.findById(any())).thenReturn(ofResult);

        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setBrand("Brand");
        productDetailDto.setCategory("Category");
        productDetailDto.setDescription("The characteristics of someone or something");
        productDetailDto.setId(123L);
        productDetailDto.setImgUrl("https://example.org/example");
        productDetailDto.setName("Name");
        productDetailDto.setPrice(BigDecimal.valueOf(42L));
        when(modelMapper.map(any(), (Class<ProductDetailDto>) any())).thenReturn(productDetailDto);
        assertEquals(2, productServiceImpl.getAllProducts(null).toList().size());
        verify(productRepository).findAll((Pageable) any());
        verify(categoryService, atLeast(1)).findById(any());
        verify(modelMapper, atLeast(1)).map(any(), (Class<ProductDetailDto>) any());
    }

    @Test
    void testSearchProducts() {
        assertTrue(productServiceImpl.searchProducts(1, "Keyword").toList().isEmpty());
        assertTrue(productServiceImpl.searchProducts(5, "Keyword").toList().isEmpty());
        assertTrue(productServiceImpl.searchProducts(0, "Keyword").toList().isEmpty());
        assertTrue(productServiceImpl.searchProducts("Query").isEmpty());
    }

    @Test
    void testFindRecentProducts() {
        assertTrue(productServiceImpl.findRecentProducts(3).isEmpty());
        assertTrue(productServiceImpl.findRecentProducts(1).isEmpty());
    }

}

