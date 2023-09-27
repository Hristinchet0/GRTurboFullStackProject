package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.dto.ProductAddDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductViewDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.repositority.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final ModelMapper modelMapper;

    private final CloudinaryService cloudinaryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    public List<ProductViewDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productEntity -> {
                    ProductViewDto productViewModel = modelMapper.map(productEntity, ProductViewDto.class);

                    Optional<Category> categoryById = categoryService.findById(productEntity.getCategory());

                    productViewModel.setCategory(categoryById.get().getName());

                    return productViewModel;
                })
                .collect(Collectors.toList());
    }

    public void addProduct(ProductAddDto productAddDto) throws IOException {
        MultipartFile img = productAddDto.getImg();
        String imageUrl = cloudinaryService.uploadImage(img);

        Product newProduct = new Product();
        newProduct.setName(productAddDto.getName());
        newProduct.setCategory(categoryService.getCategoryById(productAddDto.getCategoryId()).get());
        newProduct.setDescription(productAddDto.getDescription());
        newProduct.setPrice(productAddDto.getPrice());
        newProduct.setImgUrl(imageUrl);

        productRepository.save(newProduct);
    }


}
