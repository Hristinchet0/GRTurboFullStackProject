package com.grturbo.grturbofullstackproject.model.dto;

import java.math.BigDecimal;

public class ProductRecentDto {

    private String name;

    private String brand;

    private BigDecimal price;

    public ProductRecentDto() {
    }

    public ProductRecentDto(String name, String brand, BigDecimal price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
