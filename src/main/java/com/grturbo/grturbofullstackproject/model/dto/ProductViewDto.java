package com.grturbo.grturbofullstackproject.model.dto;

import com.grturbo.grturbofullstackproject.model.entity.Product;

public class ProductViewDto {

    private Long id;

    private String name;

    private String brand;

    private String category;

    private String price;

    private String description;

    private String imgUrl;

    public ProductViewDto() {
    }

    public ProductViewDto(Long id, String name, String brand, String category, String price, String description, String imgUrl) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public ProductViewDto(ProductViewDto productViewDto) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
