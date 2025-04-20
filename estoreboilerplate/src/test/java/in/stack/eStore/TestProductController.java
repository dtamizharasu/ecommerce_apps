package in.stack.eStore;

import in.stack.eStore.model.Products;
import in.stack.eStore.repository.ProductRepository;
import in.stack.eStore.service.ProductService;
import in.stack.eStore.utils.CommonQueries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestProductController {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

   @Test
    public void viewProductDetails(){
        List<Products> products = new ArrayList<>();
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        Products p2 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        products.add(p1);
        products.add(p2);
        String query = CommonQueries.baseQuery.replace("tableName",CommonQueries.productTableName);
        Mockito.when(productRepository.getAllProducts(query)).thenReturn(products);
        List<Products> results = productService.getAllProducts(query);
        assertNotNull(results);
        assertEquals(2,results.size(),"The Product List is not Found");
    }

    @Test
    public void viewProductsById(){
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        Mockito.when(productRepository.getProductById(p1.getProductId())).thenReturn(p1);
        Products results = productService.getProductById(p1.getProductId());
        assertNotNull(results);
        assertEquals("LG TV",results.getProductName(),"The Given Product Id Not Exists");
    }

    @Test
    public void addProductIntoList(){
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        Mockito.when(productRepository.addProduct(p1)).thenReturn(p1);
        Products results = productService.addProduct(p1);
        assertNotNull(results);
        assertEquals("LG TV",results.getProductName(),"The Given Product Details are not Added Into Product List");
    }

    @Test
    public void updateProductDetail(){
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        Mockito.when(productRepository.updateProduct(p1)).thenReturn(p1);
        Products results = productService.updateProduct(p1);
        assertNotNull(results);
        assertEquals("LG TV",results.getProductName(),"The Given Product Details are not Updated");
    }
}
