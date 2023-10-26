package com.grturbo.grturbofullstackproject.model.dto;

public class OrderDetailViewDto {

    private String productImg;

    private String productName;

    private Double productPrice;

    private int quantity;

    private Double productTotalPrice;

    public OrderDetailViewDto() {
    }

    public OrderDetailViewDto(String productImg, String productName, Double productPrice, int quantity, Double productTotalPrice) {
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(Double productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }
}
