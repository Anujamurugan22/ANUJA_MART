package com.anujamart.model;

import java.sql.Timestamp;

public class OrderItem {
    private int id;
    private int orderId;
    private int productId;
    private String productName;
    private double unitPrice;
    private int quantity;
    private Integer sellerId;
    private Timestamp createdAt;

    public OrderItem() {
    }

    public OrderItem(int id, int orderId, int productId, String productName, double unitPrice, int quantity, Integer sellerId, Timestamp createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.sellerId = sellerId;
        this.createdAt = createdAt;
    }

    public OrderItem(int productId, String productName, double unitPrice, int quantity, Integer sellerId) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.sellerId = sellerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }
}
