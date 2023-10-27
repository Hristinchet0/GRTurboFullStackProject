package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.dto.*;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductViewDto> findAll();

    void addProduct(ProductAddDto productAddDto);

    void removeProductById(Long id);

    Product getProductByID(Long id);

    Optional<Product> getProductById(Long id);

    void saveProduct(ProductEditDto productEditDto, Product product);

    Page<ProductDetailDto> getAllProducts(Pageable pageable);

    Page<ProductDetailDto> getAllProductsByCategoryId(Long id, Pageable pageable);

    Page<ProductViewDto> getAllProducts(int pageNo, int pageSize);

    List<Product> searchProducts(String query);

    List<ProductRecentDto> findRecentProducts(int count);

    Page<ProductViewDto> searchProducts(int pageNo, String keyword);
}
