package com.Project.PackageTracker.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderProduct {
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    // Constructor sin argumentos (necesario para JPA)
    public OrderProduct() {
    }

    // Constructor con argumentos
    public OrderProduct(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters y setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
