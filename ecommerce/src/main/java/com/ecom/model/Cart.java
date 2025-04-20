package com.ecom.model;

public class Cart {

    private Products products;
    private String productAddedDate;
    private String productUpdatedInCartDate;

    public String getProductAddedDate() {
        return productAddedDate;
    }

    public void setProductAddedDate(String productAddedDate) {
        this.productAddedDate = productAddedDate;
    }

    public String getProductUpdatedInCartDate() {
        return productUpdatedInCartDate;
    }

    public void setProductUpdatedInCartDate(String productUpdatedInCartDate) {
        this.productUpdatedInCartDate = productUpdatedInCartDate;
    }
    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

}
