package com.grturbo.grturbofullstackproject.model.dto;

import java.math.BigDecimal;

public class OrderDetailViewDto {

    private String productImg;

    private String productName;

    private BigDecimal productPrice;

    private int quantity;

    private BigDecimal productTotalPrice;

    public OrderDetailViewDto() {
    }

    public OrderDetailViewDto(String productImg, String productName, BigDecimal productPrice, int quantity, BigDecimal productTotalPrice) {
        this.productImg = productImg;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productTotalPrice = productTotalPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }


}
