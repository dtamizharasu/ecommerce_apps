package com.ecom.model;

import java.time.LocalDateTime;

public class OrdersDetails {
    private Products products;
    private Address address;
    private String status;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    private String itemName;

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(LocalDateTime expectDate) {
        this.expectDate = expectDate;
    }

    private LocalDateTime orderDate;
    private LocalDateTime expectDate;
    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
