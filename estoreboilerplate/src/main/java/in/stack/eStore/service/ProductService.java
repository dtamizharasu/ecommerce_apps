package in.stack.eStore.service;

import in.stack.eStore.model.Products;
import in.stack.eStore.repository.ProductRepository;
import in.stack.eStore.utils.CommonQueries;

import java.util.List;

public class ProductService implements ProductServiceInterface{

    ProductRepository productRepository =  new ProductRepository();

    public ProductService() {
        super();
    }

    @Override
    public List<Products> getAllProducts(String query) {
        return productRepository.getAllProducts(query.replace("tableName",CommonQueries.productTableName));
    }

    @Override
    public Products getProductById(int productId) {
        return productRepository.getProductById(productId);
    }

    @Override
    public Products addProduct(Products products) {
        return productRepository.addProduct(products);
    }

    @Override
    public Products updateProduct(Products products) {
        return productRepository.updateProduct(products);
    }
}
