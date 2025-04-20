package in.stack.eStore.controller;

import in.stack.eStore.exception.MaxQuantityException;
import in.stack.eStore.exception.NegativeQuantityException;
import in.stack.eStore.model.Cart;
import in.stack.eStore.model.Order;
import in.stack.eStore.model.Products;
import in.stack.eStore.service.CartService;
import in.stack.eStore.service.OrderService;
import in.stack.eStore.service.ProductService;
import in.stack.eStore.utils.CommonQueries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class OrderController {

    CartService cartService = new CartService();
    ProductService productService = new ProductService();
    public static Scanner inputScanner = new Scanner(System.in);
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    OrderService orderService = new OrderService();

    public void execute(){

        int quantity ;
        int productId = 0;
        boolean flag = true;
        int orderID = 0;
        String customerName = null;
        String doorNo = null;
        String street = null;
        String city = null;
        String state = null;
        String pinCode = null;

        try{
            while (flag) {
                System.out.println("Option 1 - View Orders Details");
                System.out.println("Option 2 - Place Orders");
                System.out.println("Option 3 - Get Order Details By OrderID");
                System.out.println("Option 4 - Cancel Orders");
                System.out.println("Option 5 - Exit Application");
                int n = inputScanner.nextInt();
                switch (n) {
                    case 1:
                        viewOrderedListDetails();
                        break;
                    case 2:
                        System.out.println("Enter the Product ID You Want TO Place Order");
                        productId = inputScanner.nextInt();
                        System.out.println("Enter the Quantity of the Product : ");
                        quantity = inputScanner.nextInt();
                        System.out.println("Enter the Customer Name");
                        customerName = reader.readLine();
                        System.out.println("Enter the Delivery Address Details");
                        System.out.println("Enter the DoorNo");
                        doorNo = reader.readLine();
                        System.out.println("Enter the Street");
                        street = reader.readLine();
                        System.out.println("Enter the City");
                        city = reader.readLine();
                        System.out.println("Enter the State");
                        state = reader.readLine();
                        System.out.println("Enter the PinCode");
                        pinCode = reader.readLine();
                        placeOrders(productId,quantity,customerName,doorNo,street,city,state,pinCode);
                        break;
                    case 3:
                        System.out.println("Please Enter the Order ID You Want to View the Order Details");
                        orderID = inputScanner.nextInt();
                        getOrderDetailsByOrderID(orderID);
                        break;
                    case 4:
                        System.out.println("Please Enter the Order ID You Want To Cancel");
                        orderID = inputScanner.nextInt();
                        cancelOrders(orderID);
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

    public void viewOrderedListDetails(){
        String query = null;
        List<Order> orderList;
        try{
            query = CommonQueries.baseQuery.replace("tableName",CommonQueries.OrderTableName);
            orderList = orderService.getAllOrdersDetails(query);

            orderList.forEach(order ->{
                String address = "DoorNo : "+order.getDoorNo()+" Street : "+order.getStreet()+" City : "+order.getCity()
                        +" State : "+order.getState()+" PinCode : "+order.getPinCode();
                System.out.println("List Of Available Products Details");
                System.out.println("------------------------------------------------------------------------");
                System.out.println("The Enter Product Details Are Below ");
                System.out.println("Order ID : "+order.getOrderId());
                System.out.println("OrderStatus : "+order.getOrderStatus());
                System.out.println("Discount : "+order.getDiscount());
                System.out.println("Customer Name : "+order.getCustomerName());
                System.out.println("Shipment Address");
                System.out.println("----------------");
                System.out.println(address);
                System.out.println("Category Type : "+order.getProducts().getItemCategory());
                System.out.println("Product ID : "+order.getProducts().getProductId());
                System.out.println("Product Name : "+order.getProducts().getProductName());
                System.out.println("Product Total Price : "+order.getProducts().getPrice());
                System.out.println("Product Quantity : "+order.getProducts().getQuantity());
                System.out.println("Product Description : "+order.getProducts().getDescription());
                System.out.println("Product Ordered Date : "+order.getOrderedDate());
                System.out.println("Product Delivery Date : "+order.getExpectedDate());
                if(!order.getOrderCancelledDate().equalsIgnoreCase("NA"))
                    System.out.println("Order Cancelled Date : "+order.getOrderCancelledDate());
                System.out.println("----------------------------------------------------------------------");

            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void placeOrders(int productID, int quantity, String name, String doorNo,String street, String city,String state,String pin){
        Random rand = new Random();
        Products existProd;
        Products cartProd;
        Cart existCart;
        Order order = new Order();
        int existCarQunat = 0;
        int remainCartQuant = 0;
        int orderID = 0;
        LocalDateTime orderedDate = LocalDateTime.now();
        LocalDateTime expectedDate = LocalDateTime.now().plusDays(5);
        try{
            existProd = productService.getProductById(productID);
            existCart = cartService.getCartProductById(productID);
            existCarQunat = existCart.getProducts().getQuantity();
            if(quantity > 0){
                if(quantity <= 50 && quantity <= existCarQunat){
                    remainCartQuant = existCarQunat - quantity;
                    orderID = rand.nextInt(1001) + 150;
                    order.setOrderId(orderID);
                    order.setOrderedDate(orderedDate.toString());
                    order.setExpectedDate(expectedDate.toString());
                    order.setDiscount(0.0);
                    cartProd = existCart.getProducts();
                    cartProd.setQuantity(quantity);
                    cartProd.setPrice(existProd.getPrice()*(double)quantity);
                    order.setProducts(cartProd);
                    order.setCustomerName(name);
                    order.setDoorNo(doorNo);
                    order.setStreet(street);
                    order.setCity(city);
                    order.setState(state);
                    order.setPinCode(pin);
                    order.setOrderStatus("Active");
                    order.setOrderCancelledDate("NA");
                    orderService.placeOrder(order);
                    if(remainCartQuant == 0){
                        cartService.removeProductFrmCart(productID);
                    }else {
                        cartProd.setQuantity(remainCartQuant);
                        existCart.setProducts(cartProd);
                        existCart.setProductUpdatedInCartDate(orderedDate.toString());
                        cartService.updateProductCart(existCart);
                    }
                }else {
                    throw new MaxQuantityException("Given Quantity is Exceeds Maximum Limits Of Carts List");
                }
            }else{
                throw new NegativeQuantityException("Given Quantity is Negative");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getOrderDetailsByOrderID(int orderId){

        String query = null;
        Order order;
        try{
            query = CommonQueries.baseQuery.replace("tableName",CommonQueries.OrderTableName);
            order = orderService.getOrderDetailsById(orderId);
            String address = "DoorNo : "+order.getDoorNo()+" Street : "+order.getStreet()+" City : "+order.getCity()
                    +" State : "+order.getState()+" PinCode : "+order.getPinCode();
            System.out.println("List Of Available Products Details");
            System.out.println("------------------------------------------------------------------------");
            System.out.println("The Enter Product Details Are Below ");
            System.out.println("Order ID : "+order.getOrderId());
            System.out.println("OrderStatus : "+order.getOrderStatus());
            System.out.println("Discount : "+order.getDiscount());
            System.out.println("Customer Name : "+order.getCustomerName());
            System.out.println("Shipment Address");
            System.out.println("----------------");
            System.out.println(address);
            System.out.println("Category Type : "+order.getProducts().getItemCategory());
            System.out.println("Product ID : "+order.getProducts().getProductId());
            System.out.println("Product Name : "+order.getProducts().getProductName());
            System.out.println("Product Total Price : "+order.getProducts().getPrice());
            System.out.println("Product Quantity : "+order.getProducts().getQuantity());
            System.out.println("Product Description : "+order.getProducts().getDescription());
            System.out.println("Product Ordered Date : "+order.getOrderedDate());
            System.out.println("Product Delivery Date : "+order.getExpectedDate());
            if(!order.getOrderCancelledDate().equalsIgnoreCase("NA"))
                System.out.println("Order Cancelled Date : "+order.getOrderCancelledDate());
            System.out.println("----------------------------------------------------------------------");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void cancelOrders(int orderID){
        Products products;
        Order order;
        int cartQuan;
        int prodQuan;
        try{
            order = orderService.getOrderDetailsById(orderID);
            products = productService.getProductById(order.getProducts().getProductId());
            cartQuan = order.getProducts().getQuantity();
            prodQuan = products.getQuantity();
            products.setQuantity(prodQuan+cartQuan);
            productService.updateProduct(products);
            orderService.cancelOrder(orderID);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

