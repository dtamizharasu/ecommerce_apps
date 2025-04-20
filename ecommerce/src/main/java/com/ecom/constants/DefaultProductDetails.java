package com.ecom.constants;

import com.ecom.model.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DefaultProductDetails {

    public static List<Products> getAvailProductDetail(){
        List<Products> productsList = new ArrayList<>();
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();
        productsList.add(new Products(91,"LG-TV",createdDate.toString(),updatedDate.toString(),25000.0,"LG Product",40,"HomeAppliances"));
        productsList.add(new Products(92,"Samsung-TV",createdDate.toString(),updatedDate.toString(),30000.0,"Samung TV",40,"HomeAppliances"));
        productsList.add(new Products(93,"Mobile",createdDate.toString(),updatedDate.toString(),20000.0,"Motorolo Mobiles",40,"Electronics"));
        productsList.add(new Products(94,"Laptop",createdDate.toString(),updatedDate.toString(),40000.0,"HP Laptops",40,"Electronics"));

        return productsList;
    }
}
