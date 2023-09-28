package com.grturbo.grturbofullstackproject.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    public OrderDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
