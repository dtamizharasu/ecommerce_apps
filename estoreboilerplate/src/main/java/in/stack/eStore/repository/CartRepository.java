package in.stack.eStore.repository;

import in.stack.eStore.model.Cart;
import in.stack.eStore.model.Products;
import in.stack.eStore.utils.CommonQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartRepository implements CartRepoInterface{

    Connection connection = null;
    public Statement statement = null;
    public ResultSet resultSet = null;
    public PreparedStatement preparedStatement = null;

    public CartRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?user=root&password=root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cart> getAllCartProducts(String query) {
        List<Cart> carts = new ArrayList<>();
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
                Cart cart = new Cart();
                cart.setProducts(product);
                cart.setProductAddedDate(resultSet.getString("productAddedDateInCart"));
                cart.setProductUpdatedInCartDate(resultSet.getString("productUpdatedDateInCart"));
                carts.add(cart);
            }
            resultSet.close();
            statement.close();
        } catch (Exception t) {
            t.printStackTrace();
        }
        return carts;
    }

    @Override
    public Cart getCartProductById(int productId) {
        Products product = new Products();
        Cart cart = new Cart();
        try {
            String sqlQuery = CommonQueries.selectQuery.replace("tableName", CommonQueries.cartsTableName).replace("?", String.valueOf(productId));
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
                    cart.setProducts(product);
                    cart.setProductAddedDate(resultSet.getString("productAddedDateInCart"));
                    cart.setProductUpdatedInCartDate(resultSet.getString("productUpdatedDateInCart"));
                }
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public Cart addProductCart(Cart cart) {
        try {
            String addQuery = CommonQueries.insertProductIntoCartsQuery;
            preparedStatement = this.connection.prepareStatement(addQuery);
            preparedStatement.setInt(1, cart.getProducts().getProductId());
            preparedStatement.setString(2, cart.getProducts().getProductName());
            preparedStatement.setString(3, cart.getProductAddedDate());
            preparedStatement.setString(4, cart.getProductUpdatedInCartDate());
            preparedStatement.setDouble(5, cart.getProducts().getPrice());
            preparedStatement.setString(6, cart.getProducts().getDescription());
            preparedStatement.setInt(7, cart.getProducts().getQuantity());
            preparedStatement.setString(8, cart.getProducts().getItemCategory());
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

        return cart;
    }

    @Override
    public Cart updateProductCart(Cart cart) {
        try {
            String addQuery = CommonQueries.updateRecordsInCartsQuery.replace("tableName",CommonQueries.cartsTableName);
            preparedStatement = this.connection.prepareStatement(addQuery);
            preparedStatement.setInt(1, cart.getProducts().getProductId());
            preparedStatement.setString(2, cart.getProducts().getProductName());
            preparedStatement.setString(3, cart.getProductUpdatedInCartDate());
            preparedStatement.setDouble(4, cart.getProducts().getPrice());
            preparedStatement.setString(5, cart.getProducts().getDescription());
            preparedStatement.setInt(6, cart.getProducts().getQuantity());
            preparedStatement.setString(7, cart.getProducts().getItemCategory());
            preparedStatement.setInt(8, cart.getProducts().getProductId());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("The Given Product Details Updated into Cart Lists");
            } else {
                System.out.println("The Given Product Details not Updated into Cart Lists");
            }
            preparedStatement.close();
        } catch (Exception se) {
            se.printStackTrace();
        }
        return cart;
    }

    @Override
    public String removeProductFrmCart(int productId) {
        String message = null;
        try {
            String query = CommonQueries.deleteRecordFrmTable.replace("tableName",CommonQueries.cartsTableName);
            System.out.println(query);
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, productId);
            int result = preparedStatement.executeUpdate();
            if(result == 1){
                message = "Given Product Details Removed From Carts : "+productId;
            }else{
                message = "Given Product Details Not Removed From Carts : "+productId;
            }

        } catch (Exception t) {
            t.printStackTrace();
        }
        return message;
    }
}
