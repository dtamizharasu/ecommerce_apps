package com.ecom.dao;

import com.ecom.constants.CommonQueries;
import com.ecom.model.Cart;
import com.ecom.model.OrdersDetails;
import com.ecom.model.Products;
import com.ecom.utils.CommonFunctions;

import java.sql.*;
import java.time.LocalDateTime;

public class DBConnection {

    public Connection connection = null;
    public Statement statement = null;
    public ResultSet resultSet = null;
    public PreparedStatement preparedStatement = null;
    private CommonFunctions commonFunctions = new CommonFunctions();

    public DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?user=root&password=root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet getRecordsFromDB(String sqlQuery){
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            closeResultSet();
            closeStatement();
            closeDBConnection();
        } catch (Exception t) {
            t.printStackTrace();
        }

        return resultSet;

    }

    boolean tableExistsSQL(String tableName) throws Exception {
        boolean flag = false;
        preparedStatement = this.connection.prepareStatement("SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?"
                + "LIMIT 1;");
        preparedStatement.setString(1, tableName);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        if (resultSet.getInt(1) != 0){
            flag =  true;
        }

        return flag;
    }

    public void createDBTableNotExists(String sqlQuery){
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            closeResultSet();
            closeStatement();
            closeDBConnection();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertProduct(Products product,String query){

        try {
            boolean tableExistCheck = tableExistsSQL(CommonQueries.productTableName);
            String TbleCreateQuery = CommonQueries.productTableCreation;
            if(!tableExistCheck){
                createDBTableNotExists(TbleCreateQuery);
            }
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,product.getProductId());
            preparedStatement.setString(2,product.getProductName());
            preparedStatement.setString(3,product.getCreatedDate());
            preparedStatement.setString(4,product.getUpdatedDate());
            preparedStatement.setDouble(5,product.getPrice());
            preparedStatement.setString(6,product.getDescription());
            preparedStatement.setInt(7,product.getQuantity());
            preparedStatement.setString(8,product.getItemCategory());
            int result = preparedStatement.executeUpdate();
            if(result==1){
                System.out.println("The Given Product Details Added into Product Lists");
            }else {
                System.out.println("The Given Product Details not Added into Product Lists");
            }
            closeResultSet();
            closeStatement();
        } catch (Exception  se) {
                System.out.println("Error Message : "+se.getMessage());
            se.printStackTrace();
        }
    }

    public void insertProductIntoCarts(Cart cart, String query){

        try {
            boolean tableExistCheck = tableExistsSQL(CommonQueries.cartsTableName);
            String TbleCreateQuery = CommonQueries.cartsTableCreation;
            if(!tableExistCheck){
                createDBTableNotExists(TbleCreateQuery);
            }
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,cart.getProducts().getItemCategory());
            preparedStatement.setInt(2,cart.getProducts().getProductId());
            preparedStatement.setString(3,cart.getProducts().getProductName());
            preparedStatement.setDouble(4,cart.getProducts().getPrice());
            preparedStatement.setInt(5,cart.getProducts().getQuantity());
            preparedStatement.setString(6,cart.getProducts().getDescription());
            preparedStatement.setString(7,cart.getProductAddedDate());
            preparedStatement.setString(8,cart.getProductUpdatedInCartDate());
            int result = preparedStatement.executeUpdate();
            if(result==1){
                System.out.println("The Given Product Details Added into Carts Lists");
            }else {
                System.out.println("The Given Product Details not Added into Carts Lists");
            }
            closeResultSet();
            closeStatement();
            closeDBConnection();

        } catch (Exception  se) {
            System.out.println(se.getMessage());

        }

    }

    public void insertProductIntoOrders(OrdersDetails ordersDetails,String query){

        try {
            boolean tableExistCheck = tableExistsSQL(CommonQueries.ordersTableName);
            String TbleCreateQuery = CommonQueries.ordersTableCreation;
            if(!tableExistCheck){
                createDBTableNotExists(TbleCreateQuery);
            }
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setLong(1,ordersDetails.getOrderId());
            preparedStatement.setString(2,ordersDetails.getStatus());
            preparedStatement.setString(3,ordersDetails.getOrderDate());
            preparedStatement.setString(4,ordersDetails.getExpectDate());
            preparedStatement.setString(5,ordersDetails.getProducts().getItemCategory());
            preparedStatement.setInt(6,ordersDetails.getProducts().getProductId());
            preparedStatement.setString(7,ordersDetails.getProducts().getProductName());
            preparedStatement.setDouble(8,ordersDetails.getProducts().getPrice());
            preparedStatement.setInt(9,ordersDetails.getProducts().getQuantity());
            preparedStatement.setString(10,ordersDetails.getProducts().getDescription());
            preparedStatement.setString(11,ordersDetails.getAddress().getName());
            preparedStatement.setString(12,ordersDetails.getAddress().getDoorNo());
            preparedStatement.setString(13,ordersDetails.getAddress().getStreet());
            preparedStatement.setString(14, ordersDetails.getAddress().getCity());
            preparedStatement.setString(15, ordersDetails.getAddress().getState());
            preparedStatement.setString(14, ordersDetails.getAddress().getPincode());
            int result = preparedStatement.executeUpdate();
            if(result==1){
                System.out.println("The selected Product has been Successfully Placed Order");
            }else {
                System.out.println("The selected Product Not Placed Order");
            }
            closeResultSet();
            closeStatement();
            closeDBConnection();
        } catch (Exception  se) {
            System.out.println(se.getMessage());

        }

    }

    public void updateRecords(String tableName, int id, int quantity, LocalDateTime updatedDate){
        ResultSet resultSet = null;
        try {
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            String query = CommonQueries.updateRecordsInProductsQuery.replace("tableName",tableName);
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2,updatedDate.toString());
            preparedStatement.setInt(3,id);
            resultSet = preparedStatement.executeQuery();
            closeResultSet();
            closeStatement();
            closeDBConnection();
        } catch (Exception t) {
            t.printStackTrace();
        }

    }

    public void updateRecordsInCarts(String tableName, int id, int quantity, LocalDateTime updatedDate, double price){
        ResultSet resultSet = null;
        try {
            Date date = new Date(0);
            String query = CommonQueries.updateRecordsInCartsQuery.replace("tableName",tableName);
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2,updatedDate.toString());
            preparedStatement.setDouble(3,price);
            preparedStatement.setInt(4,id);
            resultSet = preparedStatement.executeQuery();
            closeResultSet();
            closeStatement();
            closeDBConnection();
        } catch (Exception t) {
            t.printStackTrace();
        }

    }

    public void deleteRecord(String query, int productID){
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, productID);
            resultSet = preparedStatement.executeQuery();
            closeResultSet();
            closeStatement();
            closeDBConnection();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    public void closeDBConnection(){
        try{
            this.connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeStatement(){
        try{
            this.statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeResultSet(){
        try{
            this.resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
