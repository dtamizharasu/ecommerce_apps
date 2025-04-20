package in.stack.eStore.service;

import in.stack.eStore.model.Cart;

import java.util.List;

public interface CartServiceInterface {

    List<Cart> getAllCartProducts(String query);
    Cart getCartProductById(int productId);
    Cart addProductCart(Cart cart);
    Cart updateProductCart(Cart cart);
    String removeProductFrmCart(int productId);
}
