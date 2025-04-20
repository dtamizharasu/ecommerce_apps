package in.stack.eStore;

import in.stack.eStore.model.Cart;
import in.stack.eStore.model.Products;
import in.stack.eStore.repository.CartRepository;
import in.stack.eStore.service.CartService;
import in.stack.eStore.utils.CommonQueries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCartController {

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    CartService cartService;


    @Test
    public void viewCartProductDetails(){
        Cart cart1 = new Cart();
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        cart1.setProducts(p1);
        cart1.setProductAddedDate("2021-10-06");
        cart1.setProductUpdatedInCartDate("2021-10-06");
        Cart cart2 = new Cart();
        Products p2 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        cart2.setProducts(p2);
        cart2.setProductAddedDate("2021-10-06");
        cart2.setProductUpdatedInCartDate("2021-10-06");
        List<Cart> carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart2);
        String query = CommonQueries.baseQuery.replace("tableName",CommonQueries.cartsTableName);
        Mockito.when(cartRepository.getAllCartProducts(query)).thenReturn(carts);
        List<Cart> results = cartService.getAllCartProducts(query);
        assertNotNull(results);
        Assertions.assertEquals(2,results.size(),"The Cart Details not Found");
    }

    @Test
    public void viewCartProductsById(){
        Cart cart1 = new Cart();
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        cart1.setProducts(p1);
        Mockito.when(cartRepository.getCartProductById(p1.getProductId())).thenReturn(cart1);
        Cart results = cartService.getCartProductById(p1.getProductId());
        assertNotNull(results);
        Assertions.assertEquals("LG TV",results.getProducts().getProductName(),"The Given Product ID Not Exist in Cart");
    }

    @Test
    public void addProductIntoCartList(){
        Cart cart1 = new Cart();
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        cart1.setProducts(p1);
        Mockito.when(cartRepository.addProductCart(cart1)).thenReturn(cart1);
        Cart results = cartService.addProductCart(cart1);
        assertNotNull(results);
        Assertions.assertEquals("LG TV",results.getProducts().getProductName(),"The Given Products Details Are not Added into Cart List");
    }

    @Test
    public void updateProductInCartDetail(){
        Cart cart1 = new Cart();
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        cart1.setProducts(p1);
        Mockito.when(cartRepository.updateProductCart(cart1)).thenReturn(cart1);
        Cart results = cartService.updateProductCart(cart1);
        assertNotNull(results);
        Assertions.assertEquals("LG TV",results.getProducts().getProductName(),"The Given Product Details are not Updated in Cart List");
    }

    @Test
    public void removeProductFrmCart(){
        Cart cart1 = new Cart();
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        cart1.setProducts(p1);
        Mockito.when(cartRepository.removeProductFrmCart(p1.getProductId())).thenReturn("Given Product Details Removed From Carts : "+p1.getProductId());
        String results = cartService.removeProductFrmCart(p1.getProductId());
        assertNotNull(results);
        Assertions.assertEquals("Given Product Details Removed From Carts : 101",results,"The Given Product ID Not Removed From Cart List");
    }

}
