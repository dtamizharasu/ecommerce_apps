package in.stack.eStore.repository;

import in.stack.eStore.model.Order;
import in.stack.eStore.model.Products;
import in.stack.eStore.utils.CommonQueries;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements OrdersRepoInterface{

    Connection connection = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    public static PreparedStatement preparedStatement = null;

    public OrderRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?user=root&password=root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAllOrdersDetails(String query) {
        List<Order> orders = new ArrayList<>();
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Products product = new Products();
                product.setProductId(resultSet.getInt("productId"));
                product.setItemCategory(resultSet.getString("itemName"));
                product.setProductName(resultSet.getString("productName"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setDescription(resultSet.getString("descriptions"));
                Order order = new Order();
                order.setProducts(product);
                order.setOrderId(resultSet.getInt("orderId"));
                order.setOrderStatus(resultSet.getString("orderStatus"));
                order.setOrderedDate(resultSet.getString("orderedDate"));
                order.setExpectedDate(resultSet.getString("expectedDate"));
                order.setDiscount(resultSet.getDouble("discount"));
                order.setCustomerName(resultSet.getString("customerName"));
                order.setDoorNo(resultSet.getString("doorNo"));
                order.setState(resultSet.getString("street"));
                order.setCity(resultSet.getString("city"));
                order.setState(resultSet.getString("state"));
                order.setPinCode(resultSet.getString("pinCode"));
                order.setOrderCancelledDate(resultSet.getString("orderCancelledDate"));
                orders.add(order);
            }
            resultSet.close();
            statement.close();
        } catch (Exception t) {
            t.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getOrderDetailsById(int orderID) {
        Products product = new Products();
        Order order = new Order();
        try {
            String sqlQuery = CommonQueries.selectOrder.replace("tableName", CommonQueries.OrderTableName).replace("?", String.valueOf(orderID));
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            if(resultSet!=null){
                while (resultSet.next()) {
                    product.setProductId(resultSet.getInt("productId"));
                    product.setItemCategory(resultSet.getString("itemName"));
                    product.setProductName(resultSet.getString("productName"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setDescription(resultSet.getString("descriptions"));
                    order.setProducts(product);
                    order.setOrderId(resultSet.getInt("orderId"));
                    order.setOrderStatus(resultSet.getString("orderStatus"));
                    order.setOrderedDate(resultSet.getString("orderedDate"));
                    order.setExpectedDate(resultSet.getString("expectedDate"));
                    order.setDiscount(resultSet.getDouble("discount"));
                    order.setCustomerName(resultSet.getString("customerName"));
                    order.setDoorNo(resultSet.getString("doorNo"));
                    order.setState(resultSet.getString("street"));
                    order.setCity(resultSet.getString("city"));
                    order.setState(resultSet.getString("state"));
                    order.setPinCode(resultSet.getString("pinCode"));
                    order.setOrderCancelledDate(resultSet.getString("orderCancelledDate"));
                }
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Order placeOrder(Order order) {
        try {
            String addQuery = CommonQueries.insertProductIntoOrdersQuery;
            preparedStatement = this.connection.prepareStatement(addQuery);
            preparedStatement.setInt(1,order.getOrderId());
            preparedStatement.setString(2,order.getOrderStatus());
            preparedStatement.setString(3,order.getOrderedDate());
            preparedStatement.setString(4,order.getExpectedDate());
            preparedStatement.setDouble(5,order.getDiscount());
            preparedStatement.setString(6,order.getCustomerName());
            preparedStatement.setString(7,order.getDoorNo());
            preparedStatement.setString(8,order.getStreet());
            preparedStatement.setString(9,order.getCity());
            preparedStatement.setString(10,order.getState());
            preparedStatement.setString(11,order.getPinCode());
            preparedStatement.setInt(12, order.getProducts().getProductId());
            preparedStatement.setString(13, order.getProducts().getProductName());
            preparedStatement.setDouble(14, order.getProducts().getPrice());
            preparedStatement.setInt(15, order.getProducts().getQuantity());
            preparedStatement.setString(16, order.getProducts().getItemCategory());
            preparedStatement.setString(17, order.getProducts().getDescription());
            preparedStatement.setString(18, order.getOrderCancelledDate());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("The Given Product Details Added into Cart Lists");
            } else {
                System.out.println("The Given Product Details not Added into Cart Lists");
            }
            preparedStatement.close();
        } catch (Exception se) {
            System.out.println("Error Message : " + se.getMessage());
            se.printStackTrace();
        }

        return order;
    }

    @Override
    public String cancelOrder(int orderId) {
        String message = null;
        LocalDateTime date = LocalDateTime.now();
        try {
            String cancelQuery = CommonQueries.orderCancelQuery;
            preparedStatement = this.connection.prepareStatement(cancelQuery);
            preparedStatement.setString(1,"Cancelled");
            preparedStatement.setString(2,date.toString());
            preparedStatement.setInt(3,orderId);
            int result = preparedStatement.executeUpdate();
            if (result == 1) message = "The Given Order Has Been Cancelled Successfully " + orderId;
            else message = "The Given Order Has Not Cancelled Successfully " + orderId;
            preparedStatement.close();
        } catch (Exception se) {
            se.printStackTrace();
        }
        return message;
    }
}
