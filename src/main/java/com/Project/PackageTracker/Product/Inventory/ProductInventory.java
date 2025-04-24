package com.Project.PackageTracker.Product.Inventory;

import com.Project.PackageTracker.Branch.Branch;
import com.Project.PackageTracker.Product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "product_inventory")
public class ProductInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Branch warehouse;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "product_name")
    private String productName;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Branch getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Branch warehouse) {
        this.warehouse = warehouse;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
