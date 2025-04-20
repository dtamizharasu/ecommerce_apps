package com.ecom.model;

import java.time.LocalDateTime;

public class Products {
    public Products(int productId, String productName, LocalDateTime createdDate, LocalDateTime updatedDate, double price, String description, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    int productId;
    String productName;
    LocalDateTime createdDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    LocalDateTime updatedDate;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    double price;
    String description;
    int quantity;
}
