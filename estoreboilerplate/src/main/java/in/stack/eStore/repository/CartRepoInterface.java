package in.stack.eStore.repository;

import in.stack.eStore.model.Cart;

import java.util.List;

public interface CartRepoInterface {

    List<Cart> getAllCartProducts(String query);
    Cart getCartProductById(int productId);
    Cart addProductCart(Cart products);
    Cart updateProductCart(Cart products);
    String removeProductFrmCart(int productId);
}
