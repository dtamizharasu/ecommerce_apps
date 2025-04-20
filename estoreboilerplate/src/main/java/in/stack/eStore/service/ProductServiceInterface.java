package in.stack.eStore.service;

import in.stack.eStore.model.Products;

import java.util.List;

public interface ProductServiceInterface {

	List<Products> getAllProducts(String query);
	Products getProductById(int productId);
	Products addProduct(Products products);
	Products updateProduct(Products products);
}
