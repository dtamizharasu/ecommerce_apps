package com.ecom.executor;

import com.ecom.exception.InvalidPriceException;
import com.ecom.exception.MaxQuantityException;
import com.ecom.exception.NegativeQuantityException;
import com.ecom.service.EcomService;
import java.io.IOException;
import java.util.*;

public class EcomApp {
    public static Scanner inputScanner = new Scanner(System.in);
    public static void main(String args[]) throws NegativeQuantityException, InvalidPriceException, MaxQuantityException, IOException {

        while (true){
            System.out.println("Option 1 - View Available Products Details");
            System.out.println("Option 2 - Adding the Product To Cart");
            System.out.println("Option 3 - Remove Products From Cart");
            System.out.println("Option 4 - Place Orders");
            System.out.println("Option 5 - View Orders");
            System.out.println("Option 6 - View Cart Details");
            System.out.println("Option 7 - Add Products");
            System.out.println("Option 8 - Exit Application");
            int n = inputScanner.nextInt();
            switch (n){
                case 1: EcomService.viewProductDetails(); break;
                case 2: EcomService.addCart(); break;
                case 3: EcomService.removeCart(); break;
                case 4: EcomService.placeOrders(); break;
                case 5: EcomService.viewOrders(); break;
                case 6: EcomService.viewCart(); break;
                case 7: EcomService.addProducts(); break;
                case 8: System.out.println("Exit Application");
                System.exit(0); break;

            }
        }
    }



}
