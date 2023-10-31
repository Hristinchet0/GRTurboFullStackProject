package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.model.dto.*;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.repositority.ProductRepository;
import com.grturbo.grturbofullstackproject.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryServiceImpl categoryServiceImpl;

    private final ModelMapper modelMapper;

    private final CloudinaryServiceImpl cloudinaryServiceImpl;

    public ProductServiceImpl(ProductRepository productRepository, CategoryServiceImpl categoryServiceImpl, ModelMapper modelMapper, CloudinaryServiceImpl cloudinaryServiceImpl) {
        this.productRepository = productRepository;
        this.categoryServiceImpl = categoryServiceImpl;
        this.modelMapper = modelMapper;
        this.cloudinaryServiceImpl = cloudinaryServiceImpl;
    }

    @Override
    public List<ProductViewDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productEntity -> {
                    ProductViewDto productViewModel = modelMapper.map(productEntity, ProductViewDto.class);

                    Optional<Category> categoryById = categoryServiceImpl.findById(productEntity.getCategory());

                    productViewModel.setCategory(categoryById.get().getName());

                    return productViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addProduct(ProductAddDto productAddDto) {
        MultipartFile img = productAddDto.getImg();
        String imageUrl = cloudinaryServiceImpl.uploadImage(img);

        Product newProduct = new Product();
        newProduct.setName(productAddDto.getName());
        newProduct.setBrand(productAddDto.getBrand());
        newProduct.setCategory(categoryServiceImpl.findCategoryById(productAddDto.getCategoryId()).get());
        newProduct.setDescription(productAddDto.getDescription());
        newProduct.setPrice(productAddDto.getPrice());
        newProduct.setImgUrl(imageUrl);

        productRepository.save(newProduct);
    }

    @Override
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductByID(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void saveProduct(ProductEditDto productEditDto, Product product) {

        MultipartFile img = productEditDto.getImg();

        String currentImg = product.getImgUrl();

        product.setName(productEditDto.getName());
        product.setBrand(productEditDto.getBrand());
        product.setCategory(categoryServiceImpl.findCategoryById(productEditDto.getCategoryId()).get());
        product.setDescription(productEditDto.getDescription());
        product.setPrice(productEditDto.getPrice());

        if (img.isEmpty()) {
            product.setImgUrl(currentImg);
        } else {
            String imageUrl = cloudinaryServiceImpl.uploadImage(img);
            product.setImgUrl(imageUrl);
        }

        productRepository.save(product);
    }

    @Override
    public Page<ProductDetailDto> getAllProducts(Pageable pageable) {
        return productRepository
                .findAll(pageable)
                .map(product -> {
                    ProductDetailDto productDetailDto = modelMapper.map(product, ProductDetailDto.class);
                    Optional<Category> categoryById = categoryServiceImpl.findById(product.getCategory());

                    productDetailDto.setCategory(categoryById.get().getName());

                    return productDetailDto;
                });
    }

    @Override
    public Page<ProductDetailDto> getAllProductsByCategoryId(Long id, Pageable pageable) {
        return productRepository.findByCategoryId(id, pageable)
                .map(product -> {
                    ProductDetailDto productDetailDto = modelMapper.map(product, ProductDetailDto.class);

                    Optional<Category> categoryById = categoryServiceImpl.findById(product.getCategory());

                    productDetailDto.setCategory(categoryById.get().getName());

                    return productDetailDto;
                });

    }

    @Override
    public Page<ProductViewDto> getAllProducts(int pageNo, int pageSize) {
        List<ProductViewDto> productDtoList = findAll();
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return toPage(productDtoList, pageable);
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.findAllByNameOrDescription(query);
    }

    @Override
    public List<ProductRecentDto> findRecentProducts(int count) {
        return productRepository.findRecentProducts(PageRequest.of(0, count, Sort.by("id").descending()));
    }

    @Override
    public Page<ProductViewDto> searchProducts(int pageNo, String keyword) {
        List<ProductViewDto> productDtoList = productRepository.findAllByNameOrDescription(keyword)
                .stream()
                .map(product -> {
                    ProductViewDto productViewDto = modelMapper.map(product, ProductViewDto.class);

                    Optional<Category> categoryById = categoryServiceImpl.findById(product.getCategory());

                    productViewDto.setCategory(categoryById.get().getName());

                    return productViewDto;
        })
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(pageNo, 5);
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
}
