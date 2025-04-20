package in.stack.eStore.model;

public class Products {
    public Products(int productId, String productName, String createdDate, String updatedDate, double price, String description, int quantity,String itemCategory) {
        this.productId = productId;
        this.productName = productName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.itemCategory = itemCategory;
    }

    int productId;
    String productName;
    String createdDate;
    String itemCategory;

    public Products() {
        super();
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    String updatedDate;

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
