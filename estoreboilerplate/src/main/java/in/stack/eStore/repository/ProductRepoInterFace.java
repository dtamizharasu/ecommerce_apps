package in.stack.eStore.repository;

import java.util.List;

import in.stack.eStore.model.Products;

public interface ProductRepoInterFace {
	
	List<Products> getAllProducts(String query);
	Products getProductById(int productId);
	Products addProduct(Products products);
	Products updateProduct(Products products);
	
  
}
