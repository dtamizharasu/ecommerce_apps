package in.stack.eStore.service;


import in.stack.eStore.model.Cart;
import in.stack.eStore.repository.CartRepository;

import java.util.List;

public class CartService implements CartServiceInterface {

    CartRepository cartRepository = new CartRepository();

    @Override
    public List<Cart> getAllCartProducts(String query) {
        return cartRepository.getAllCartProducts(query);
    }

    @Override
    public Cart getCartProductById(int productId) {
        return cartRepository.getCartProductById(productId);
    }

    @Override
    public Cart addProductCart(Cart cart) {
        return cartRepository.addProductCart(cart);
    }

    @Override
    public Cart updateProductCart(Cart cart) {
        return cartRepository.updateProductCart(cart);
    }

    @Override
    public String removeProductFrmCart(int productId) {
        return cartRepository.removeProductFrmCart(productId);
    }
}
