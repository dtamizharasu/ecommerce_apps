package in.stack.eStore.controller;

import in.stack.eStore.exception.InvalidPriceException;
import in.stack.eStore.exception.MaxQuantityException;
import in.stack.eStore.exception.NegativeQuantityException;
import in.stack.eStore.model.Cart;
import in.stack.eStore.model.Products;
import in.stack.eStore.service.CartService;
import in.stack.eStore.service.ProductService;
import in.stack.eStore.utils.CommonQueries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CartController {

    CartService cartService = new CartService();
    ProductService productService = new ProductService();
    public static Scanner inputScanner = new Scanner(System.in);
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void execute(){
        String productName;
        int quantity ;
        int option = 0;
        int productId = 0;
        double price;
        String description;
        boolean flag = true;
        try{
            while (flag) {
                System.out.println("Option 1 - View Cart Products Details");
                System.out.println("Option 2 - Adding the Product To Cart List / Update Product Details Into Cart List");
                System.out.println("Option 3 - Get Product Details By Product ID From Cart");
                System.out.println("Option 4 - Remove Products From Cart List");
                System.out.println("Option 5 - Exit Application");
                int n = inputScanner.nextInt();
                switch (n) {
                    case 1:
                        viewAllProductsInCart();
                        break;
                    case 2:
                        System.out.println("Please Choose the below Option Whether You Want Add Or Update Product");
                        System.out.println("option 1 - Add");
                        System.out.println("option 2 - Update");
                        int options = inputScanner.nextInt();
                        String userAction = (options == 1) ? "Add" : "Update";
                        System.out.println("Please select Category of the Product : HomeAppliance or Electronics You Want to "+userAction);
                        System.out.println("option 1 - HomeAppliances");
                        System.out.println("option 2 - Electronics");
                        int opt = inputScanner.nextInt();
                        String productType = (opt == 1) ? "HomeAppliances" : "Electronics";
                        System.out.println("Enter Product Name : ");
                        productName = reader.readLine();
                        System.out.println("Enter the Quantity of the Product : ");
                        quantity = inputScanner.nextInt();
                        System.out.println("Enter the Product Price");
                        price = inputScanner.nextDouble();
                        System.out.println("Enter the Product Description");
                        description = reader.readLine();
                        System.out.println("Enter the Product ID");
                        productId = inputScanner.nextInt();
                        if(userAction.equalsIgnoreCase("Add"))
                            addProductIntoCart(productId,productName,price,description,quantity,productType);
                        else
                            updateProductInCart(productId,productName,price,description,quantity,productType);
                        break;
                    case 3:
                        System.out.println("Please Enter the Product ID You Want to View the Product Details");
                        productId = inputScanner.nextInt();
                        viewProductsById(productId);
                        break;
                    case 4:
                        System.out.println("Please Enter the Product ID You Want to View the Product Details");
                        productId = inputScanner.nextInt();
                        System.out.println("Enter the Quantity You want to Remove From Carts");
                        quantity = inputScanner.nextInt();
                        removeProductFrmCart(productId,quantity);
                        break;
                    case 5:
                        System.out.println("Exit Cart Services");
                        flag = false;
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewAllProductsInCart(){

        try{
            String query = CommonQueries.baseQuery.replace("tableName",CommonQueries.cartsTableName);
            List<Cart> carts = cartService.getAllCartProducts(query);
            System.out.println("List Of Available Products Details");
            carts.forEach(p ->{
                System.out.println("---------------------------------------------");
                System.out.println("Category Type : "+p.getProducts().getItemCategory());
                System.out.println("Product ID : "+p.getProducts().getProductId());
                System.out.println("Product Name : "+p.getProducts().getProductName());
                System.out.println("Product Price : "+p.getProducts().getPrice());
                System.out.println("Product Quantity : "+p.getProducts().getQuantity());
                System.out.println("Product Description : "+p.getProducts().getDescription());
                System.out.println("Product Created Date : "+p.getProductAddedDate());
                System.out.println("Product Updated Date : "+p.getProductUpdatedInCartDate());
                System.out.println("---------------------------------------------");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewProductsById(int productID){
        Cart cart;
        try{
            cart = cartService.getCartProductById(productID);
            System.out.println("The Enter Product Details Are Below ");
            System.out.println("Category Type : "+cart.getProducts().getItemCategory());
            System.out.println("Product ID : "+cart.getProducts().getProductId());
            System.out.println("Product Name : "+cart.getProducts().getProductName());
            System.out.println("Product Price : "+cart.getProducts().getPrice());
            System.out.println("Product Quantity : "+cart.getProducts().getQuantity());
            System.out.println("Product Description : "+cart.getProducts().getDescription());
            System.out.println("Product Created Date : "+cart.getProductAddedDate());
            System.out.println("Product Updated Date : "+cart.getProductUpdatedInCartDate());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addProductIntoCart(int productId, String productName,double price, String description, int quantity, String itemCategory){
        Cart cart = new Cart();
        Cart exitCart;
        Products products = new Products();
        Products productsDetails;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();
        int existQuantityInCart =0;
        int exitProdQuant = 0;
        try{
            productsDetails = productService.getProductById(productId);
            exitProdQuant = productsDetails.getQuantity();

            if(price > 0){
                if(quantity > 0){
                    if(quantity <= 50 && quantity <= exitProdQuant){
                        products.setProductId(productId);
                        products.setProductName(productName);
                        products.setDescription(description);
                        products.setItemCategory(itemCategory);
                        cart.setProductUpdatedInCartDate(updatedDate.toString());
                        exitCart = cartService.getCartProductById(productId);
                        if(exitCart.getProductAddedDate()!=null){
                            existQuantityInCart = exitCart.getProducts().getQuantity()+quantity;
                            products.setQuantity(existQuantityInCart);
                            double lastestPrice = productsDetails.getPrice() * (double) existQuantityInCart;
                            products.setPrice(lastestPrice);
                            cart.setProducts(products);
                            cartService.updateProductCart(cart);
                        }else{
                            cart.setProductAddedDate(createdDate.toString());
                            products.setQuantity(quantity);
                            products.setPrice(productsDetails.getPrice()*(double)quantity);
                            cart.setProducts(products);
                            cartService.addProductCart(cart);
                        }

                        int updateProdQuant = exitProdQuant - quantity;
                        productsDetails.setQuantity(updateProdQuant);
                        productsDetails.setUpdatedDate(updatedDate.toString());
                        productService.updateProduct(productsDetails);
                    }else {
                        throw new MaxQuantityException("Given Quantity is Exceeds Maximum Limits");
                    }
                }else{
                    throw new NegativeQuantityException("Given Quantity is Negative");
                }
            }else {
                throw new InvalidPriceException("Give Price Amount is Not Valida");
            }

        }catch (Exception e){
        }
    }

    public void updateProductInCart(int productId, String productName,double price, String description, int quantity, String itemCategory){
        Products products = new Products();
        Cart cart = new Cart();
        Cart oldCart = new Cart();
        LocalDateTime updatedDate = LocalDateTime.now();
        try{
            Products oldProds = productService.getProductById(productId);
            oldCart = cartService.getCartProductById(productId);
            int exitQuantity = oldProds.getQuantity() + oldCart.getProducts().getQuantity() ;
            if(price > 0){
                if(quantity > 0){
                    if(quantity <= 50 && quantity <= exitQuantity){
                        products.setProductId(productId);
                        products.setProductName(productName);
                        products.setUpdatedDate(updatedDate.toString());
                        products.setPrice(price*(double)quantity);
                        products.setQuantity(quantity);
                        products.setDescription(description);
                        products.setItemCategory(itemCategory);
                        cart.setProducts(products);
                        cart.setProductUpdatedInCartDate(updatedDate.toString());
                        cartService.updateProductCart(cart);
                        int updPrdCount = exitQuantity - quantity;
                        oldProds.setQuantity(updPrdCount);
                        oldProds.setUpdatedDate(updatedDate.toString());
                        productService.updateProduct(oldProds);

                    }else {
                        throw new MaxQuantityException("Given Quantity is Exceeds Maximum Limits");
                    }
                }else{
                    throw new NegativeQuantityException("Given Quantity is Negative");
                }
            }else {
                throw new InvalidPriceException("Give Price Amount is Not Valida");
            }

        }catch (Exception e){
        }
    }

    public void removeProductFrmCart(int productId, int quantity){
        try{
            Products products = new Products();
            Cart cart = new Cart();
            LocalDateTime updatedDate = LocalDateTime.now();
            try{
                Products oldProds = productService.getProductById(productId);
                Cart exitsCart = cartService.getCartProductById(productId);
                int exitQuantity = oldProds.getQuantity() + quantity ;
                int existCarCount = exitsCart.getProducts().getQuantity();
                    if(quantity > 0){
                        if(quantity <= 50 && quantity <= existCarCount){
                            int diffCount = existCarCount - quantity;
                            if(diffCount==0){
                                cartService.removeProductFrmCart(productId);
                            }else {
                                products.setProductId(productId);
                                products.setProductName(oldProds.getProductName());
                                products.setPrice(oldProds.getPrice()*(double) diffCount);
                                products.setQuantity(diffCount);
                                products.setDescription(oldProds.getDescription());
                                products.setItemCategory(oldProds.getItemCategory());
                                cart.setProducts(products);
                                cart.setProductUpdatedInCartDate(updatedDate.toString());
                                cartService.updateProductCart(cart);
                            }
                            oldProds.setQuantity(exitQuantity);
                            oldProds.setUpdatedDate(updatedDate.toString());
                            productService.updateProduct(oldProds);

                        }else {
                            throw new MaxQuantityException("Given Quantity is Exceeds Maximum Limits");
                        }
                }else {
                    throw new InvalidPriceException("Give Price Amount is Not Valida");
                }

            }catch (Exception e){
            }

        }catch (Exception e){

        }
    }
}
