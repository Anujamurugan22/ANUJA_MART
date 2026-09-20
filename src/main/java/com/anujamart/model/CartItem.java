package com.anujamart.model;

import java.sql.Timestamp;

public class CartItem {
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private Timestamp createdAt;

    // Associated Product information for convenient display in cart
    private Product product;

    public CartItem() {
    }

    public CartItem(int id, int userId, int productId, int quantity, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public CartItem(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSubtotal() {
        if (product != null) {
            return product.getPrice() * quantity;
        }
        return 0.0;
    }
}
