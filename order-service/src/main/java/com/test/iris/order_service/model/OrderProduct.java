package com.test.iris.order_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
  //  @Column(name = "order_id")
    private Order order;

    @Column(name = "product_id")
    private Long productId;

    public OrderProduct() {
    }

    public OrderProduct(Long id, Order order, Long productId) {
        this.id = id;
        this.order = order;
        this.productId = productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", order=" + order +
                ", productId=" + productId +
                '}';
    }
}
