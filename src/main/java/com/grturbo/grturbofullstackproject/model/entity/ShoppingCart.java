package com.grturbo.grturbofullstackproject.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private User customer;

    private Double totalPrice;

    private Integer totalItems;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartItem> cartItems;



    public ShoppingCart() {
        this.cartItems = new HashSet<>();
        this.totalItems = 0;
        this.totalPrice = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", customer=" + customer +
                ", totalPrice=" + totalPrice +
                ", totalItems=" + totalItems +
                ", cartItems=" + cartItems +
                '}';
    }
}
