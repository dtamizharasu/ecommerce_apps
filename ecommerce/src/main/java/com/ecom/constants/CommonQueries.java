package com.ecom.constants;

public class CommonQueries {

    public static String productTableName = "products";
    public static String ordersTableName = "orders";
    public static String cartsTableName = "carts";
    public static String baseQuery = "select * from tableName";
    public static String selectQuery = "select * from tableName where productName=?";
    public static String updateRecordsInProductsQuery = "update tableName set quantity = ?,updatedDate = ? where productId = ?";
    public static String updateRecordsInCartsQuery = "update tableName set quantity = ?,updatedDate = ?,price = ? where productId = ?";
    public static String insertProductIntoProductQuery = "insert into products values(?,?,?,?,?,?,?)";
    public static String insertProductIntoCartsQuery = "insert into carts values(?,?,?,?,?,?,?,?,?,?)";
    public static String insertProductIntoOrdersQuery = "insert into orders values(?,?,?,?,?,?,?,?,?,?,?,?)";
    public static String deleteRecordFrmTable = "delete from tableName where product_id = ?";
    public static String productTableCreation = "create table if not exists products(\n" +
            " productId int not null,\n" +
            " productName varchar(50),\n" +
            " createdDate date,\n" +
            " updatedDate date,\n" +
            " price double,\n" +
            " descriptions varchar(500),\n" +
            " quantity int,\n" +
            "CONSTRAINT pk_productId primary key (productId)\n" +
            " );";
    public static String cartsTableCreation = "create table if not exists carts(\n" +
            " itemName varchar(500),\n" +
            " productId int not null,\n" +
            " productName varchar(50),\n" +
            " price double,\n" +
            " quantity int,\n" +
            " descriptions varchar(500),\n" +
            " productAddedDateInCart date,\n" +
            " productUpdatedDateInCart date,\n" +
            " CONSTRAINT fk_productId FOREIGN KEY (productId) REFERENCES products(productId)\n" +
            " );";
    public static String ordersTableCreation = "create table if not exists orders(\n" +
            " orderId long not null,\n" +
            " orderStatus varchar(50),\n" +
            " orderDate date,\n" +
            " expectDate date,\n" +
            " itemName varchar(500),\n" +
            " productId int not null,\n" +
            " productName varchar(50),\n" +
            " price double,\n" +
            " quantity int,\n" +
            " descriptions varchar(500),\n" +
            " customerName varchar(100),\n" +
            " doorNo varchar(20),\n" +
            " street varchar(200),\n" +
            " city varchar(20),\n" +
            " state varchar(20),\n" +
            " pincode varchar(50),\n" +
            " CONSTRAINT fk_od_productId FOREIGN KEY (productId) REFERENCES products(productId)\n" +
            " );";
}
