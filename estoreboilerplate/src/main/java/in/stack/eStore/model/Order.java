package in.stack.eStore.model;

public class Order {

    private int orderId;
    private String orderStatus;
    private String orderedDate;
    private String expectedDate;
    private double discount;
    private String customerName;
    private String doorNo;
    private String street;
    private String city;
    private String state;
    private String pinCode;
    private Products products;
    private String OrderCancelledDate;

    public Order() {
        super();
    }

    public Order(int orderId, String orderStatus, String orderedDate, String expectedDate, double discount, String customerName, String doorNo, String street, String city, String state, String pinCode, Products products, String orderCancelledDate) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderedDate = orderedDate;
        this.expectedDate = expectedDate;
        this.discount = discount;
        this.customerName = customerName;
        this.doorNo = doorNo;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.products = products;
        OrderCancelledDate = orderCancelledDate;
    }

    public String getOrderCancelledDate() {
        return OrderCancelledDate;
    }

    public void setOrderCancelledDate(String orderCancelledDate) {
        OrderCancelledDate = orderCancelledDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
}
