package com.ecom.service;


import com.ecom.constants.DefaultProductDetails;
import com.ecom.exception.InvalidPriceException;
import com.ecom.exception.MaxQuantityException;
import com.ecom.exception.NegativeQuantityException;
import com.ecom.model.*;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

public class EcomService {

    public static Scanner inputScanner = new Scanner(System.in);
    public static Map<Integer, Cart> carts = new HashMap<>();
    public static List<OrdersDetails> ordersDetail = new ArrayList<>();
    public static Map<Integer,Categories> categoriesMap = new TreeMap<>();

    public static void addCart(){

        System.out.println("Please select Category of the Product : HomeAppliance or Electronics");
        System.out.println("option 1 - HomeAppliance");
        System.out.println("option 2 - Electronics");
        int opt = inputScanner.nextInt();
        String productType = (opt==1) ? "HomeAppliance" : "Electronics";
        System.out.println("Enter Product Name : ");
        String product = inputScanner.next();
        System.out.println("Enter the Quantity of the Product : ");
        int quantity = inputScanner.nextInt();
        System.out.println("Enter the Product ID : ");
        int productID = inputScanner.nextInt();
        Cart cart = new Cart();

                if(categoriesMap.containsKey(productID)){

                    if (carts.size() != 0 && carts.containsKey(productID)) {

                        int latestquantity = carts.get(productID).getProducts().getQuantity()+quantity;
                        double latestPrice = latestquantity * carts.get(productID).getProducts().getPrice();
                        Products products = carts.get(productID).getProducts();
                        products.setQuantity(latestquantity);
                        products.setPrice(latestPrice);
                        cart.setItemName(productType);
                        cart.setProducts(products);

                    }else{
                        Categories categories = categoriesMap.get(productID);
                        Products products = categories.getProducts();
                        double latestPrice = quantity * categoriesMap.get(productID).getProducts().getPrice();
                        products.setQuantity(quantity);
                        products.setPrice(latestPrice);
                        cart.setItemName(productType);
                        cart.setProducts(products);
                    }

                }else {
                    System.out.println("Please Enter Available Product ID");
                }

        carts.put(productID,cart);

        carts.forEach((k,f) -> {
            System.out.println(f.getItemName());
            System.out.println(f.getProducts().getProductId());
            System.out.println(f.getProducts().getProductName());
            System.out.println(f.getProducts().getQuantity());

        });

    }


    public static void removeCart(){

        System.out.println("Please Enter the Product ID to Remove From Carts List");
        int productID = inputScanner.nextInt();
        System.out.println("Please Enter How Many Quantity want to be removed");
        int count = inputScanner.nextInt();
        int existCount =0;
        if (carts.containsKey(productID)){
            existCount = carts.get(productID).getProducts().getQuantity();
            if(count == existCount){
                carts.remove(productID);
            }else
            {
                if(existCount>count){
                    existCount = carts.get(productID).getProducts().getQuantity();
                    Cart cartDetails = carts.get(productID);
                    int latCount = existCount-count;
                    double latestPrice = latCount * carts.get(productID).getProducts().getPrice();
                    Products prods = carts.get(productID).getProducts();
                    prods.setQuantity(existCount-count);
                    prods.setPrice(latestPrice);
                    cartDetails.setProducts(prods);
                    int productId = prods.getProductId();
                    carts.put(productId,cartDetails);
                }
                else {
                    System.out.println("Please Enter the Valid Count");
                }
            }

            System.out.println("The Enter the Product Removed Succssfully from the Cart List : "+productID);
        }else {
            System.out.println("Enter Product not Wishlisted");
        }
        System.out.println("Latest Cart List Details");
        carts.forEach((k,f) -> {
            System.out.println(f.getItemName());
            System.out.println(f.getProducts().getProductId());
            System.out.println(f.getProducts().getProductName());
            System.out.println(f.getProducts().getQuantity());

        });
    }

    public static void placeOrders() {

        System.out.println("Please Select the Product ID You want to Place Order");
        int productID = inputScanner.nextInt();
        System.out.println("Enter the Quantity You want to Place Order");
        int count = inputScanner.nextInt();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime expectDate = today.plusDays(5);
        Cart cartDetails = carts.get(productID);
        Address address = new Address("Tamizharasu D", "5B", "AP Main Road", "Salem", "Tamilnadu", "636104");
        OrdersDetails ordersDetails = new OrdersDetails();
        Products products = cartDetails.getProducts();
        products.setPrice(products.getPrice()*products.getQuantity());
        ordersDetails.setItemName(cartDetails.getItemName());
        ordersDetails.setProducts(cartDetails.getProducts());
        ordersDetails.setAddress(address);
        ordersDetails.setOrderDate(today);
        ordersDetails.setExpectDate(expectDate);
        ordersDetails.setStatus("Placed Order");
        ordersDetail.add(ordersDetails);
        EcomService ecomService = new EcomService();
        ecomService.writerOrderToFile(ordersDetail);
        int existCount = carts.get(productID).getProducts().getQuantity();
        if (count == existCount) {
            carts.remove(productID);
        } else {
            if (existCount > count) {
                existCount = carts.get(productID).getProducts().getQuantity();
                Products prods = carts.get(productID).getProducts();
                int productId = prods.getProductId();
                prods.setQuantity(existCount - count);
                cartDetails.setProducts(prods);
                carts.put(productId, cartDetails);
            } else {
                System.out.println("Please Enter the Valid Count");
            }
        }
    }
    public static void viewOrders(){

        ordersDetail.forEach(f -> {
            System.out.println(f.getItemName());
            System.out.println(f.getProducts().getProductId());
            System.out.println(f.getProducts().getProductName());
            System.out.println(f.getProducts().getQuantity());
            System.out.println(f.getProducts().getPrice());
            System.out.println(f.getProducts().getDescription());
            System.out.println(f.getAddress().toString());
            System.out.println(f.getOrderDate());
            System.out.println(f.getExpectDate());
            System.out.println(f.getStatus());
        });

    }

    public static void viewCart(){
        System.out.println("Latest Cart List Details");
        carts.forEach((k,f) -> {
            System.out.println(f.getItemName());
            System.out.println(f.getProducts().getProductId());
            System.out.println(f.getProducts().getProductName());
            System.out.println(f.getProducts().getQuantity());
            System.out.println(f.getProducts().getPrice());

        });
    }

    public static void addProducts() throws InvalidPriceException, NegativeQuantityException, MaxQuantityException, IOException {

        Map<Integer,Categories> productDetails = getProductDetails();

        int option = 0;
        String itemName= null;
        int productId = 0;
        String productName = null;
        double price = 0.0;
        String description = null;
        int quantity = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Categories categories = new Categories();

        System.out.println("Enter the Category You Want to Add into Main List");
        System.out.println("Option 1 HomeAppliance");
        System.out.println("Option 2 Electronics");

        if(inputScanner.hasNextInt()){
            option = inputScanner.nextInt();
        }
        String productType = (option == 1) ? "HomeAppliance" : "Electronics";
        System.out.println("Enter the Product Id : ");
        if(inputScanner.hasNextInt()) {
            productId = inputScanner.nextInt();
        }

        System.out.println("Enter the Product price : ");
                price = inputScanner.nextDouble();
                    if(price >0){
                    }else {
                        throw new InvalidPriceException("Please Enter Valid Price");
                    }

            System.out.println("Please Enter the Valid Quantity :");
            quantity = inputScanner.nextInt();
                if (quantity > 0 ) {
                    if (quantity <= 20) {
                    } else {
                        throw new MaxQuantityException("Given Quantity Exceed the Maximum Quantity Size"); }
                } else {
                    throw new NegativeQuantityException("Given Quantity size is Negative");}

        System.out.println("Enter the Product Name : ");
        productName = reader.readLine();
        System.out.println("Please Enter the Product Description");
        description = reader.readLine();

        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();

        Products products = new Products(productId,productName,createdDate,updatedDate,price,description,quantity);
        categories.setProductType(productType);
        categories.setProducts(products);

        productDetails.put(productId,categories);

        System.out.println("The Given Product Details Added Successfully into Product List");

    }

    public static  Map<Integer,Categories> getProductDetails(){
        Map<Integer,Categories> categoriesList = DefaultProductDetails.getAvailProductDetail();

        if(carts.size() == 0){
            categoriesMap= categoriesList;
        }else {
            categoriesList.forEach((productkey,value) -> {

                if(carts.containsKey(productkey) && categoriesList.containsKey(productkey)){
                    Categories categories = categoriesList.get(productkey);
                    Cart cart = carts.get(productkey);
                    System.out.println("categories.getProducts().getQuantity() :"+categories.getProducts().getQuantity());
                    System.out.println("cart.getProducts().getQuantity() :"+cart.getProducts().getQuantity());
                    int Quantity = categories.getProducts().getQuantity()-cart.getProducts().getQuantity();
                    Products products = categories.getProducts();
                    products.setQuantity(Quantity);
                    LocalDateTime updatedDate = LocalDateTime.now();
                    products.setUpdatedDate(updatedDate);
                    categories.setProducts(products);
                    categoriesMap.put(productkey,categories);
                }else {
                    Categories categories = categoriesList.get(productkey);
                    Products products = categories.getProducts();
                    categoriesMap.put(products.getProductId(),categories);
                }
            });
        }

        return  categoriesMap;
    }

    public static void displayProducts(Map<Integer,Categories> categoriesList){

        categoriesList.forEach((key,value)->{

            System.out.println("Category Type : "+value.getProductType());
            System.out.println("Product ID : "+value.getProducts().getProductId());
            System.out.println("Product Name : "+value.getProducts().getProductName());
            System.out.println("Product Price : "+value.getProducts().getPrice());
            System.out.println("Product Quantity : "+value.getProducts().getQuantity());
            System.out.println("Product Description : "+value.getProducts().getDescription());
            System.out.println("Product Created Date : "+value.getProducts().getCreatedDate());
            System.out.println("Product Upadted Date : "+value.getProducts().getUpdatedDate());
        });

    }

    public static void viewProductDetails(){
        displayProducts(getProductDetails());
    }

    public void writerOrderToFile(List<OrdersDetails> ordersDetails){
        EcomService ecomService = new EcomService();
        BufferedWriter bufferedWriter = null;
        ArrayList<String> result = new ArrayList<>();
        try{
            File file= new File ("C:\\Users\\tamiz\\IdeaProjects\\Ecom App\\src\\main\\resources\\order_details.txt");
            if (!file.exists())
            {
                file.createNewFile();
            }else {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    while (br.ready()) {
                        result.add(br.readLine());
                    }
                }
            }
            bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\tamiz\\IdeaProjects\\Ecom App\\src\\main\\resources\\order_details.txt"));
            for (OrdersDetails order : ordersDetails){
                bufferedWriter.write(ecomService.getOrderDetail(order));
                bufferedWriter.write(result.toString());
            }
            bufferedWriter.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        }

    }

    public String getOrderDetail(OrdersDetails order){
        String result = null;

        String dooNo = order.getAddress().getDoorNo();
        String name = order.getAddress().getName();
        String street = order.getAddress().getStreet();
        String city = order.getAddress().getCity();
        String state = order.getAddress().getState();
        String pinCode = order.getAddress().getPincode();
        int productID = order.getProducts().getProductId();
        String productName = order.getProducts().getProductName();
        String price = String.valueOf(order.getProducts().getPrice());
        String quantity = String.valueOf(order.getProducts().getQuantity());
        String orderedDate = String.valueOf(order.getOrderDate());
        String expectedDate = String.valueOf(order.getExpectDate());
        String status = order.getStatus();
        String productType = order.getItemName();
        String productDetail = "Product Details are "+"Product Type : "+productType+" ProductName : "+productName+" ProductID : "+productID+" Price RS."+price+" Quantity of Product : "+quantity;
        String message = " The following order has been Placed Successfully and ";
        String address = "Customer Name : "+name+" DoorNo : "+dooNo+" Street : "+street+" city : "+city+" state : "+state+" PinCode - "+pinCode;
        String dates = " Order Placed Date : "+orderedDate+" and Order Expect to Deliver on : "+expectedDate;
        result = message+productDetail+" Customer Address Details - "+address+dates;

        return result;
    }
}
