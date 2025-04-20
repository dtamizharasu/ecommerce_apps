package com.ecom.constants;

import com.ecom.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DefaultProductDetails {

    public static Map<Integer,Categories> getAvailProductDetail(){
        Map<Integer,Categories> categories = new TreeMap<>();
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();
        Categories categorie1 = new Categories();
        categorie1.setProductType("HomeAppliance");
        categorie1.setProducts(new Products(101,"LG-TV",createdDate,updatedDate,25000.0,"LG Product",5));
        Categories categorie2 = new Categories();
        categorie2.setProductType("HomeAppliance");
        categorie2.setProducts(new Products(102,"Samsung-TV",createdDate,updatedDate,30000.0,"Samung TV",5));
        Categories categorie3 = new Categories();
        categorie3.setProductType("Electronics");
        categorie3.setProducts(new Products(201,"Mobile",createdDate,updatedDate,20000.0,"Motorolo Mobiles",5));
        Categories categorie4 = new Categories();
        categorie4.setProductType("Electronics");
        categorie4.setProducts(new Products(202,"Laptop",createdDate,updatedDate,40000.0,"HP Laptops",5));

        categories.put(categorie1.getProducts().getProductId(),categorie1);
        categories.put(categorie2.getProducts().getProductId(),categorie2);
        categories.put(categorie3.getProducts().getProductId(),categorie3);
        categories.put(categorie4.getProducts().getProductId(),categorie4);


        return categories;
    }
}
