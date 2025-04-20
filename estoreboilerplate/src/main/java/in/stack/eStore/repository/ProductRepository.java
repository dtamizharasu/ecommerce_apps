package in.stack.eStore.repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import in.stack.eStore.model.Products;
import in.stack.eStore.utils.CommonQueries;

public class ProductRepository implements  ProductRepoInterFace {

	Connection connection = null;
	public Statement statement = null;
	public ResultSet resultSet = null;
	public PreparedStatement preparedStatement = null;

	public ProductRepository() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?user=root&password=root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get All Products
	@Override
	public List<Products> getAllProducts(String sqlQuery) {
		List<Products> products = new ArrayList<>();
		try {
			statement = this.connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				Products product = new Products();
				product.setProductId(resultSet.getInt("productId"));
				product.setItemCategory(resultSet.getString("itemName"));
				product.setProductName(resultSet.getString("productName"));
				product.setPrice(resultSet.getDouble("price"));
				product.setQuantity(resultSet.getInt("quantity"));
				product.setDescription(resultSet.getString("descriptions"));
				product.setCreatedDate(resultSet.getString("createdDate"));
				product.setUpdatedDate(resultSet.getString("updatedDate"));
				products.add(product);
			}
			resultSet.close();
			statement.close();
		} catch (Exception t) {
			t.printStackTrace();
		}
		return products;
	}

	// Get Particular Product with given ProductId
	@Override
	public Products getProductById(int productID) {
		Products product = new Products();
		try {
			String sqlQuery = CommonQueries.selectQuery.replace("tableName", CommonQueries.productTableName).replace("?", String.valueOf(productID));
			statement = this.connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				product.setProductId(resultSet.getInt("productId"));
				product.setItemCategory(resultSet.getString("itemName"));
				product.setProductName(resultSet.getString("productName"));
				product.setPrice(resultSet.getDouble("price"));
				product.setQuantity(resultSet.getInt("quantity"));
				product.setDescription(resultSet.getString("descriptions"));
				product.setCreatedDate(resultSet.getString("createdDate"));
				product.setUpdatedDate(resultSet.getString("updatedDate"));
			}
			resultSet.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	// Add the Product Into List
	@Override
	public Products addProduct(Products product) {
		try {
			String addQuery = CommonQueries.insertProductIntoProductQuery;
			preparedStatement = this.connection.prepareStatement(addQuery);
			preparedStatement.setInt(1, product.getProductId());
			preparedStatement.setString(2, product.getProductName());
			preparedStatement.setString(3, product.getCreatedDate());
			preparedStatement.setString(4, product.getUpdatedDate());
			preparedStatement.setDouble(5, product.getPrice());
			preparedStatement.setString(6, product.getDescription());
			preparedStatement.setInt(7, product.getQuantity());
			preparedStatement.setString(8, product.getItemCategory());
			int result = preparedStatement.executeUpdate();
			if (result == 1) {
				System.out.println("The Given Product Details Added into Product Lists");
			} else {
				System.out.println("The Given Product Details not Added into Product Lists");
			}
			preparedStatement.close();
		} catch (Exception se) {
			System.out.println("Error Message : " + se.getMessage());
			se.printStackTrace();
		}

		return product;
	}

	// Update the Product Details
	@Override
	public Products updateProduct(Products product) {
		try {
			String addQuery = CommonQueries.updateRecordsInProductsQuery.replace("tableName",CommonQueries.productTableName);
			preparedStatement = this.connection.prepareStatement(addQuery);
			preparedStatement.setInt(1, product.getProductId());
			preparedStatement.setString(2, product.getProductName());
			preparedStatement.setString(3, product.getUpdatedDate());
			preparedStatement.setDouble(4, product.getPrice());
			preparedStatement.setString(5, product.getDescription());
			preparedStatement.setInt(6, product.getQuantity());
			preparedStatement.setString(7, product.getItemCategory());
			preparedStatement.setInt(8, product.getProductId());
			int result = preparedStatement.executeUpdate();
			if (result == 1) {
				System.out.println("The Given Product Details Updated into Product Lists");
			} else {
				System.out.println("The Given Product Details not Updated into Product Lists");
			}
			preparedStatement.close();
		} catch (Exception se) {
			se.printStackTrace();
		}
		return product;
	}

}