package com.workshop.products;

import com.google.common.collect.ImmutableList;
import com.workshop.products.dto.Product;

import java.util.List;

public class TestUtils {

    public static final String PRODUCT_REF = "123";

    public static List<Product> getListProducts() {
        Product product_1 =  getProduct(PRODUCT_REF, "product_1", "first test product", 10);
        Product product_2 =  getProduct("abc", "product_2", "second test product", 20);
        return ImmutableList.of(product_1, product_2);
    }

    public static List<Product> getListProductsForFrontend() {
        Product product_1 =  getProduct("111", "Agile Testing", "A Practical Guide for Testers and Agile Teams", 5);
        return ImmutableList.of(product_1);
    }

    public static Product getProduct(String ref, String name, String description, int price) {
        return Product.builder()
                .ref(ref)
                .name(name)
                // .shortName(name)  /****** Breaking contract demo *****/
                // .description(description)  /****** Breaking contract demo *****/
                .price(price)
                .build();
    }

}
