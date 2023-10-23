package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.dto.*;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.repositority.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
        newProduct.setBrand(productAddDto.getBrand());
        newProduct.setCategory(categoryService.getCategoryById(productAddDto.getCategoryId()).get());
        newProduct.setDescription(productAddDto.getDescription());
        newProduct.setPrice(productAddDto.getPrice());
        newProduct.setImgUrl(imageUrl);

        productRepository.save(newProduct);
    }


    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductByID(Long id) {
        return productRepository.findProductById(id);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


    public void saveProduct(ProductEditDto productEditDto, Product product) throws IOException {

        MultipartFile img = productEditDto.getImg();

        String currentImg = product.getImgUrl();

        product.setName(productEditDto.getName());
        product.setBrand(productEditDto.getBrand());
        product.setCategory(categoryService.getCategoryById(productEditDto.getCategoryId()).get());
        product.setDescription(productEditDto.getDescription());
        product.setPrice(productEditDto.getPrice());

        if (img.isEmpty()) {
            product.setImgUrl(currentImg);
        } else {
            String imageUrl = cloudinaryService.uploadImage(img);
            product.setImgUrl(imageUrl);
        }

        productRepository.save(product);
    }

    public Page<ProductDetailDto> getAllProducts(Pageable pageable) {

        return productRepository
                .findAll(pageable)
                .map(product -> {
                    ProductDetailDto productDetailDto = modelMapper.map(product, ProductDetailDto.class);
                    Optional<Category> categoryById = categoryService.findById(product.getCategory());

                    productDetailDto.setCategory(categoryById.get().getName());

                    return productDetailDto;
                });
    }

    public Page<ProductDetailDto> getAllProductsByCategoryId(Long id, Pageable pageable) {

        return productRepository.findByCategoryId(id, pageable)
                .map(product -> {
                    ProductDetailDto productDetailDto = modelMapper.map(product, ProductDetailDto.class);

                    Optional<Category> categoryById = categoryService.findById(product.getCategory());

                    productDetailDto.setCategory(categoryById.get().getName());

                    return productDetailDto;
                });

    }


    public Page<ProductViewDto> getAllProducts(int pageNo, int pageSize) {
        List<ProductViewDto> productDtoList = findAll();
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return toPage(productDtoList, pageable);
    }

    private Page<ProductViewDto> toPage(List<ProductViewDto> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        if (start > end) {
            return Page.empty();
        }

        List<ProductViewDto> subList = list.subList(start, end);
        return new PageImpl<>(subList, pageable, list.size());
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findAllByNameOrDescription(query);
    }

    public List<ProductRecentDto> findRecentProducts(int count) {
        List<ProductRecentDto> recentProducts = productRepository.findRecentProducts(PageRequest.of(0, count, Sort.by("id").descending()));

        return recentProducts;
    }

    public Page<ProductViewDto> searchProducts(int pageNo, String keyword) {
        List<ProductViewDto> productDtoList = productRepository.findAllByNameOrDescription(keyword)
                .stream()
                .map(product -> {
                    ProductViewDto productViewDto = modelMapper.map(product, ProductViewDto.class);

                    Optional<Category> categoryById = categoryService.findById(product.getCategory());

                    productViewDto.setCategory(categoryById.get().getName());

                    return productViewDto;
        })
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<ProductViewDto> dtoPage = toPage(productDtoList, pageable);
        return dtoPage;
    }
}
