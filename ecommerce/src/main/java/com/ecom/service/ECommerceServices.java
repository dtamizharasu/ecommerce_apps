package com.ecom.service;


import com.ecom.constants.CommonQueries;
import com.ecom.constants.DefaultProductDetails;
import com.ecom.dao.DBConnection;
import com.ecom.exception.InvalidPriceException;
import com.ecom.exception.MaxQuantityException;
import com.ecom.exception.NegativeQuantityException;
import com.ecom.model.*;
import com.ecom.utils.CommonFunctions;

import java.io.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.*;

public class ECommerceServices {

    public static Scanner inputScanner = new Scanner(System.in);
    public static DBConnection dbConnection = new DBConnection();
    public static CommonFunctions commonFunctions = new CommonFunctions();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public void execute() {
        ECommerceServices eCommerceServices = new ECommerceServices();
        String productName;
        int quantity ;
        int option = 0;
        int productId = 0;
        double price;
        String description;
        eCommerceServices.UpdateDefaultProductDetails(DefaultProductDetails.getAvailProductDetail());
        try {
            while (true) {
                System.out.println("Option 1 - View Available Products Details");
                System.out.println("Option 2 - Adding the Product To Cart");
                System.out.println("Option 3 - Remove Products From Cart");
                System.out.println("Option 4 - Place Orders");
                System.out.println("Option 5 - View Orders");
                System.out.println("Option 6 - View Cart Details");
                System.out.println("Option 7 - Add Products To Product List");
                System.out.println("Option 8 - Exit Application");
                int n = inputScanner.nextInt();
                switch (n) {
                    case 1:
                        eCommerceServices.viewProductDetails(CommonQueries.baseQuery.replace("tableName",CommonQueries.productTableName));
                        break;
                    case 2:
                        System.out.println("Please select Category of the Product : HomeAppliance or Electronics");
                        System.out.println("option 1 - HomeAppliances");
                        System.out.println("option 2 - Electronics");
                        int opt = inputScanner.nextInt();
                        String productType = (opt == 1) ? "HomeAppliances" : "Electronics";
                        System.out.println("Enter Product Name : ");
                        productName = inputScanner.next();
                        System.out.println("Enter the Quantity of the Product : ");
                        quantity = inputScanner.nextInt();
                        eCommerceServices.addCart(productType,productName,quantity);
                        break;
                    case 3:
                        System.out.println("Please Enter the Product Name to Remove From Carts List");
                        productName = inputScanner.next();
                        System.out.println("Please Enter How Many Quantity want to be removed");
                        quantity = inputScanner.nextInt();
                        eCommerceServices.removeCart(productName,quantity);
                        break;
                    case 4:
                        System.out.println("Please Select the Product Name You want to Place Order");
                        productName = inputScanner.next();
                        System.out.println("Enter the Quantity You want to Place Order");
                        quantity = inputScanner.nextInt();
                        eCommerceServices.placeOrders(productName,quantity);
                        break;
                    case 5:
                        eCommerceServices.viewOrders(CommonQueries.baseQuery.replace("tableName",CommonQueries.ordersTableName));
                        break;
                    case 6:
                        eCommerceServices.viewCart(CommonQueries.baseQuery.replace("tableName",CommonQueries.cartsTableName));
                        break;
                    case 7:
                        System.out.println("Enter the Category You Want to Add into Main List");
                        System.out.println("Option 1 HomeAppliances");
                        System.out.println("Option 2 Electronics");
                        if(inputScanner.hasNextInt()){
                            option = inputScanner.nextInt();
                        }
                        productType = (option == 1) ? "HomeAppliances" : "Electronics";
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
                        System.out.println("Enter the Product Name : ");
                        productName = reader.readLine();
                        System.out.println("Please Enter the Product Description");
                        description = reader.readLine();
                        System.out.println("Please Enter the Valid Quantity :");
                        quantity = inputScanner.nextInt();
                        eCommerceServices.addProducts(productId,productName,price,description,quantity,productType);
                        break;
                    case 8:
                        System.out.println("Exit Application");
                        System.exit(0);
                        break;
                }
            }
        }catch (Exception e){

        }
    }

    /*
    * Adding the Product Details Into Wishlists */

    public void addCart(String productType, String productName, int quantity){

        try{
            String query = CommonQueries.selectQuery.replace("tableName",CommonQueries.productTableName).replace("?", productName);
            int availableQuantity = 0;
            ResultSet resultSet = dbConnection.getRecordsFromDB(query);
            while (resultSet.next()) {
                availableQuantity = resultSet.getInt("quantity");
            }
            if (quantity > 0 ) {
                if (quantity <= availableQuantity) {
                    System.out.println("Entered Quantity for Products Accepted");
                    int productId = resultSet.getInt("productId");
                    LocalDateTime createdDated = commonFunctions.convertToLocalDateTimeViaInstant(resultSet.getDate("createdDate"));
                    LocalDateTime updatedDate = LocalDateTime.now();
                    double price = resultSet.getDouble("price");
                    String descriptions = resultSet.getString("descriptions");
                    int latestCount = availableQuantity - quantity;
                    Products products = new Products(productId,productName,createdDated.toString(),updatedDate.toString(),price,descriptions,latestCount,productType);
                    dbConnection.updateRecords(CommonQueries.productTableName,productId,latestCount,updatedDate);

                    String cartQuery = CommonQueries.selectQuery.replace("tableName",CommonQueries.cartsTableName).replace("?", productName);
                    ResultSet cartResult = dbConnection.getRecordsFromDB(cartQuery);
                    if(cartResult.next()){
                        int updateQuantity = quantity + cartResult.getInt("quantity");
                        double updatedPrice = price * (double) updateQuantity;
                        LocalDateTime updatedTime = LocalDateTime.now();
                      dbConnection.updateRecordsInCarts(CommonQueries.cartsTableName,cartResult.getInt("productId"),updateQuantity,updatedTime,updatedPrice);

                    }
                    else
                    {
                        Cart cart = new Cart();
                        double productPrice = price * (double) quantity;
                        cart.setProducts(products);
                        Products productUpdate = cart.getProducts();
                        productUpdate.setPrice(productPrice);
                        cart.setProducts(productUpdate);
                        cart.setProductAddedDate(updatedDate.toString());
                        cart.setProductUpdatedInCartDate(updatedDate.toString());
                        dbConnection.insertProductIntoCarts(cart,CommonQueries.insertProductIntoCartsQuery);
                        System.out.println("Selected Product Added Successfully into Carts List");
                    }
                } else {
                    throw new MaxQuantityException("Given Quantity Exceed the Maximum Quantity Size"); }
            } else {
                throw new NegativeQuantityException("Given Quantity size is Negative");
            }


        }catch (Exception e){

        }

    }

    /*
    * Removing the Product Details From WishList*/

    public void removeCart(String productName,int enteredQuantity){

        try{
            int availableQuantityinCarts = 0;
            String query = CommonQueries.selectQuery.replace("tableName",CommonQueries.cartsTableName).replace("?", productName);
            ResultSet resultSetCarts = dbConnection.getRecordsFromDB(query);
            while (resultSetCarts.next()) {
                availableQuantityinCarts = resultSetCarts.getInt("quantity");
            }
            if (enteredQuantity > 0 ) {
                if (enteredQuantity <= availableQuantityinCarts) {
                    System.out.println("Entered Quantity for Products Accepted");

                    String queryProduct = CommonQueries.selectQuery.replace("tableName",CommonQueries.productTableName).replace("?", productName);
                    ResultSet resultSetProds = dbConnection.getRecordsFromDB(queryProduct);
                    resultSetProds.next();
                    int productId = resultSetProds.getInt("productId");
                    LocalDateTime updatedDate = LocalDateTime.now();
                    double price = resultSetProds.getDouble("price");
                    int latestCount = resultSetProds.getInt("quantity") + enteredQuantity;
                    dbConnection.updateRecords(CommonQueries.productTableName,productId,latestCount,updatedDate);

                    int latestCountCarts = availableQuantityinCarts - enteredQuantity;
                    if(latestCountCarts == 0){
                        dbConnection.deleteRecord(CommonQueries.deleteRecordFrmTable.replace("tableName",CommonQueries.cartsTableName),productId);
                    }else{
                        double productPrice = price * (double) latestCountCarts;
                        dbConnection.updateRecordsInCarts(CommonQueries.cartsTableName,productId,latestCount,updatedDate,productPrice);
                        System.out.println("Selected Product Added Successfully into Carts List");
                    }

                } else {
                    throw new MaxQuantityException("Given Quantity Exceed the Maximum Quantity Size"); }
            } else {
                throw new NegativeQuantityException("Given Quantity size is Negative");
            }

        }catch (Exception e){

        }

    }

    /*Placing the Orders */

    public void placeOrders(String productName, int quantity) {

        try{
            int availableQuantityinCarts = 0;
            String query = CommonQueries.selectQuery.replace("tableName",CommonQueries.cartsTableName).replace("?", productName);
            ResultSet resultSetCarts = dbConnection.getRecordsFromDB(query);
            while (resultSetCarts.next()) {
                availableQuantityinCarts = resultSetCarts.getInt("quantity");
            }
            if (quantity > 0 ) {
                if (quantity <= availableQuantityinCarts) {
                    String queryProduct = CommonQueries.selectQuery.replace("tableName",CommonQueries.productTableName).replace("?", productName);
                    ResultSet resultSetProds = dbConnection.getRecordsFromDB(queryProduct);
                    resultSetProds.next();
                    int productId = resultSetProds.getInt("productId");
                    double price = resultSetProds.getDouble("price");
                    double overallPrdPrice = price * (double)quantity;
                    LocalDateTime orderedDate = LocalDateTime.now();
                    LocalDateTime expectDate = orderedDate.plusDays(5);
                    String description = resultSetProds.getString("descriptions");
                    String itemCategory = resultSetProds.getString("itemName");
                    long orderID = (long)(Math.random()*(1000-100+1)+100);
                    OrdersDetails ordersDetails = new OrdersDetails();
                    Products products = new Products(productId,productName,orderedDate.toString(),orderedDate.toString(),overallPrdPrice,description,quantity,itemCategory);
                    ordersDetails.setProducts(products);
                    ordersDetails.setOrderId(orderID);
                    ordersDetails.setOrderDate(orderedDate.toString());
                    ordersDetails.setExpectDate(expectDate.toString());
                    Address address = new Address("Tamizharasu D","5B","AP Main Road","Salem","Tamilnadu","636104");
                    ordersDetails.setAddress(address);
                    ordersDetails.setStatus("Order Placed");
                    dbConnection.insertProductIntoOrders(ordersDetails,CommonQueries.insertProductIntoOrdersQuery);
                    writerOrderToFile(ordersDetails);
                    int latestCountCarts = availableQuantityinCarts - quantity;
                    if(latestCountCarts == 0){
                        dbConnection.deleteRecord(CommonQueries.deleteRecordFrmTable.replace("tableName",CommonQueries.cartsTableName),productId);
                    }else{
                        double productPrice = price * (double) latestCountCarts;
                        dbConnection.updateRecordsInCarts(CommonQueries.cartsTableName,productId,latestCountCarts,orderedDate,productPrice);
                    }

                } else {
                    throw new MaxQuantityException("Given Quantity Exceed the Maximum Quantity Size"); }
            } else {
                throw new NegativeQuantityException("Given Quantity size is Negative");
            }

        }catch (Exception e){

        }


    }

    /*Viewing the Ordered Products Details*/

    public void viewOrders(String query){
        System.out.println("List Of Order Details");
        try{
            ResultSet resultSet = dbConnection.getRecordsFromDB(query);
            while (resultSet.next()){
                System.out.println("Category Type : "+resultSet.getString("itemName"));
                System.out.println("Product ID : "+resultSet.getString("product_id"));
                System.out.println("Product Name : "+resultSet.getString("productName"));
                System.out.println("Product Price : "+resultSet.getDouble("price"));
                System.out.println("Product Quantity : "+resultSet.getInt("quantity"));
                System.out.println("Product Description : "+resultSet.getString("descriptions"));
                System.out.println("Product Ordered Date : "+resultSet.getDate("createdDate"));
                System.out.println("Product Expected Delivery Date : "+resultSet.getDate("updatedDate"));
                System.out.println("Customer Name : "+resultSet.getString("customerName"));
                System.out.println("DoorNo : "+resultSet.getString("doorNo"));
                System.out.println("Street : "+resultSet.getString("street"));
                System.out.println("City : "+resultSet.getString("street"));
                System.out.println("State : "+resultSet.getString("state"));
                System.out.println("PinCode : "+resultSet.getString("pincode"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    /*Viewing the Wishlist Details*/

    public void viewCart(String query){
        System.out.println("Latest Cart List Details");
        try{
            ResultSet resultSet = dbConnection.getRecordsFromDB(query);
            while (resultSet.next()){
                System.out.println("Category Type : "+resultSet.getString("itemName"));
                System.out.println("Product ID : "+resultSet.getString("product_id"));
                System.out.println("Product Name : "+resultSet.getString("productName"));
                System.out.println("Product Price : "+resultSet.getDouble("price"));
                System.out.println("Product Quantity : "+resultSet.getInt("quantity"));
                System.out.println("Product Description : "+resultSet.getString("descriptions"));
                System.out.println("Product Added Date : "+resultSet.getDate("createdDate"));
                System.out.println("Product Updated Date : "+resultSet.getDate("updatedDate"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /*Adding the new Products into Products List*/

    public void addProducts(int productId, String productName, double price, String description, int quantity, String productType){
        Products products;
        try{
            LocalDateTime createdDate = LocalDateTime.now();
            LocalDateTime updatedDate = LocalDateTime.now();
            ResultSet productExists = dbConnection.getRecordsFromDB(CommonQueries.selectQuery.replace("tableName",CommonQueries.productTableName).replace("?",productName));
            if(productExists.next()){
                int exitsQuantity = productExists.getInt("quantity") + quantity;
                if (quantity > 0 ) {
                    if (quantity <= 50 ) {
                        if(exitsQuantity <= 50){
                            products = new Products(productId,productName,createdDate.toString(),updatedDate.toString(),price,description,exitsQuantity,productType);
                            dbConnection.insertProduct(products,CommonQueries.insertProductIntoProductQuery);
                            System.out.println("The Given Product Details has been Added Successfully into Products List");
                        }else {
                            throw new MaxQuantityException("Given Quantity Exceed the Maximum Quantity Size");
                        }
                    } else {
                        throw new MaxQuantityException("Given Quantity Exceed the Maximum Quantity Size"); }
                } else {
                    throw new NegativeQuantityException("Given Quantity size is Negative");
                }
            }else{
                if (quantity > 0 ) {
                    if (quantity <= 50 ) {
                        products = new Products(productId,productName,createdDate.toString(),updatedDate.toString(),price,description,quantity,productType);
                        dbConnection.insertProduct(products,CommonQueries.insertProductIntoProductQuery);
                        System.out.println("The Given Product Details has been Added Successfully into Products List");
                    } else {
                        throw new MaxQuantityException("Given Quantity Exceed the Maximum Quantity Size"); }
                } else {
                    throw new NegativeQuantityException("Given Quantity size is Negative");}
            }

            System.out.println("The Given Product Details Added Successfully into Product List");
        }catch (Exception e){

        }

    }

    /*Adding the Default Products Details into Products List*/

    public  void UpdateDefaultProductDetails(List<Products> productsList){
        for (Products products : productsList){
            dbConnection.insertProduct(products,CommonQueries.insertProductIntoProductQuery);
        }
        dbConnection.closeDBConnection();
    }

    /*
    * Viewing the Exiting Products Details
    * */
    public void viewProductDetails(String query){

        try{
            ResultSet resultSet = dbConnection.getRecordsFromDB(query);
            while (resultSet.next()){
                System.out.println("Category Type : "+resultSet.getString("itemName"));
                System.out.println("Product ID : "+resultSet.getString("product_id"));
                System.out.println("Product Name : "+resultSet.getString("productName"));
                System.out.println("Product Price : "+resultSet.getDouble("price"));
                System.out.println("Product Quantity : "+resultSet.getInt("quantity"));
                System.out.println("Product Description : "+resultSet.getString("descriptions"));
                System.out.println("Product Created Date : "+resultSet.getDate("createdDate"));
                System.out.println("Product Updated Date : "+resultSet.getDate("updatedDate"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /*Writing the Orders Logs Into Resources Log Folder*/

    public void writerOrderToFile(OrdersDetails ordersDetails){
        ECommerceServices ecomService = new ECommerceServices();
        BufferedWriter bufferedWriter;
        try{
            File file= new File ("C:\\Users\\tamiz\\IdeaProjects\\Ecom App\\src\\main\\resources\\logs\\order_details.txt");
            if (!file.exists())
            {
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\tamiz\\IdeaProjects\\Ecom App\\src\\main\\resources\\logs\\order_details.txt"));
            bufferedWriter.write(ecomService.getOrderDetail(ordersDetails));
            bufferedWriter.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    /*
    * Forming the Log Message to Log into Log File*/

    public String getOrderDetail(OrdersDetails order){
        String result;

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
        String productDetail = "Product Details are "+"Product Type : "+order.getProducts().getItemCategory()+" ProductName : "+productName+" ProductID : "+productID+" Price RS."+price+" Quantity of Product : "+quantity;
        String message = " The following order has been Placed Successfully and ";
        String address = "Customer Name : "+name+" DoorNo : "+dooNo+" Street : "+street+" city : "+city+" state : "+state+" PinCode - "+pinCode;
        String dates = " Order Placed Date : "+orderedDate+" and Order Expect to Deliver on : "+expectedDate;
        result = message+productDetail+" Customer Address Details - "+address+dates;

        return result;
    }
}
