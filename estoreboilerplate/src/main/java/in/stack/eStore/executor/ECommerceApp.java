package in.stack.eStore.executor;

import in.stack.eStore.controller.CartController;
import in.stack.eStore.controller.OrderController;
import in.stack.eStore.controller.ProductController;
import in.stack.eStore.utils.CommonQueries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ECommerceApp {

    public static Scanner inputScanner = new Scanner(System.in);
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String args[]) {
        ProductController productController = new ProductController();
        CartController cartController = new CartController();
        OrderController orderController = new OrderController();
        while (true) {
            System.out.println("Option 1 - Product Services");
            System.out.println("Option 2 - Cart Services");
            System.out.println("Option 3 - Orders Services");
            System.out.println("Option 4 - Exit Application");
            int n = inputScanner.nextInt();
            switch (n) {
                case 1:
                    productController.execute();
                    break;
                case 2:
                    cartController.execute();
                    break;
                case 3:
                    orderController.execute();
                    break;
                case 4:
                    System.out.println("Exits");
                    System.exit(0);
                    break;

            }

        }
    }
}
