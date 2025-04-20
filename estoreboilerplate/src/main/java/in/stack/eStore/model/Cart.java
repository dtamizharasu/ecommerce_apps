package in.stack.eStore.model;

public class Cart {

    private Products products;
    private String productAddedDate;
    private String productUpdatedInCartDate;

    public Cart(Products products, String productAddedDate, String productUpdatedInCartDate) {
        this.products = products;
        this.productAddedDate = productAddedDate;
        this.productUpdatedInCartDate = productUpdatedInCartDate;
    }

    public Cart() {
        super();
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

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
}
