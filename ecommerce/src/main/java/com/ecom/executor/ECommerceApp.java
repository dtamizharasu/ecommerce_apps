package com.ecom.executor;

import com.ecom.exception.InvalidPriceException;
import com.ecom.exception.MaxQuantityException;
import com.ecom.exception.NegativeQuantityException;
import com.ecom.service.ECommerceServices;

import java.io.IOException;

public class ECommerceApp {
    public static void main(String args[]) throws InvalidPriceException, NegativeQuantityException, MaxQuantityException, IOException {

        ECommerceServices eCommerceServices = new ECommerceServices();
        eCommerceServices.execute();
    }



}
