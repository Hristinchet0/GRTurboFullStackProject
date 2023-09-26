package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.repositority.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
