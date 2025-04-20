package in.stack.eStore.controller;

import in.stack.eStore.exception.InvalidPriceException;
import in.stack.eStore.exception.MaxQuantityException;
import in.stack.eStore.exception.NegativeQuantityException;
import in.stack.eStore.model.Products;
import in.stack.eStore.service.ProductService;
import in.stack.eStore.utils.CommonQueries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ProductController {

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
                System.out.println("Option 1 - View Available Products Details");
                System.out.println("Option 2 - Adding the Product To Product List / Update Product Details Into Product List");
                System.out.println("Option 3 - Get Product Details By Product ID");
                System.out.println("Option 4 - Exit Application");
                int n = inputScanner.nextInt();
                switch (n) {
                    case 1:
                        viewProductDetails(CommonQueries.baseQuery);
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
                            addProductIntoList(productId,productName,price,description,quantity,productType);
                        else
                            updateProductDetail(productId,productName,price,description,quantity,productType);
                        break;
                    case 3:
                        System.out.println("Please Enter the Product ID You Want to View the Product Details");
                        productId = inputScanner.nextInt();
                        viewProductsById(productId);
                        break;
                    case 4:
                        System.out.println("Exit Product Services");
                        flag = false;
                        break;
                }
            }
        }catch (Exception e){

        }
    }

    public void viewProductDetails(String query){
        List<Products> products;
        try{
            products = productService.getAllProducts(query);
            System.out.println("List Of Available Products Details");
            products.forEach(p ->{
                System.out.println("---------------------------------------------");
                System.out.println("Category Type : "+p.getItemCategory());
                System.out.println("Product ID : "+p.getProductId());
                System.out.println("Product Name : "+p.getProductName());
                System.out.println("Product Price : "+p.getPrice());
                System.out.println("Product Quantity : "+p.getQuantity());
                System.out.println("Product Description : "+p.getDescription());
                System.out.println("Product Created Date : "+p.getCreatedDate());
                System.out.println("Product Updated Date : "+p.getUpdatedDate());
                System.out.println("---------------------------------------------");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewProductsById(int productID){
        Products p;
        try{
            p = productService.getProductById(productID);
            if(p.getProductName()!=null){
                System.out.println("The Enter Product Details Are Below ");
                System.out.println("Category Type : "+p.getItemCategory());
                System.out.println("Product ID : "+p.getProductId());
                System.out.println("Product Name : "+p.getProductName());
                System.out.println("Product Price : "+p.getPrice());
                System.out.println("Product Quantity : "+p.getQuantity());
                System.out.println("Product Description : "+p.getDescription());
                System.out.println("Product Created Date : "+p.getCreatedDate());
                System.out.println("Product Updated Date : "+p.getUpdatedDate());
            }else {
                System.out.println("Note -- The Entered Product Id Details are Not Available In List");
                System.out.println("Please Enter valid Id");

            }

        }catch (Exception e){

        }

    }

    public void addProductIntoList(int productId, String productName,double price, String description, int quantity, String itemCategory){
        Products products = new Products();
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();
        try{
            if(price > 0){
                if(quantity > 0){
                    if(quantity <= 50){
                        products.setProductId(productId);
                        products.setProductName(productName);
                        products.setCreatedDate(createdDate.toString());
                        products.setUpdatedDate(updatedDate.toString());
                        products.setPrice(price);
                        products.setQuantity(quantity);
                        products.setDescription(description);
                        products.setItemCategory(itemCategory);
                        productService.addProduct(products);
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

    public void updateProductDetail(int productId, String productName,double price, String description, int quantity, String itemCategory){
        Products products = new Products();
        LocalDateTime updatedDate = LocalDateTime.now();
        try{
            Products oldProds = productService.getProductById(productId);
            int exitQuantity = oldProds.getQuantity() + quantity ;
            if(price > 0){
                if(quantity > 0){
                    if(quantity <= 50 && exitQuantity <= 50){
                        products.setProductId(productId);
                        products.setProductName(productName);
                        products.setUpdatedDate(updatedDate.toString());
                        products.setPrice(price);
                        products.setQuantity(exitQuantity);
                        products.setDescription(description);
                        products.setItemCategory(itemCategory);
                        productService.updateProduct(products);
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

}
