package com.workshop.cart;

import com.workshop.cart.dto.Cart;
import com.workshop.cart.dto.Product;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    static final String PRODUCT_REF_1 = "123";
    static final String PRODUCT_REF_2 = "abc";
    static final String PRODUCT_REF_3 = "999";
    static final Product PRODUCT_1_WITH_DETAILS = getProduct(PRODUCT_REF_1,"product_1", 10);
    static final Product PRODUCT_2_WITH_DETAILS = getProduct(PRODUCT_REF_2,"product_2", 20);

    public static Cart getTestCart() {
        Product product_1 =  getProduct(PRODUCT_REF_1,null, 0);
        Product product_2 =  getProduct(PRODUCT_REF_2, null, 0);
        return new Cart(new ArrayList<>(List.of(product_1,product_2)), 0);
    }

    public static Cart getExpectedCartWithDetails() {
        return new Cart(new ArrayList<>(List.of(PRODUCT_1_WITH_DETAILS,PRODUCT_2_WITH_DETAILS)), 30);
    }

    public static Product getProduct(String ref, String name, int price) {
        return Product.builder()
                .ref(ref)
                .name(name)
             //   .shortName(name)  /***** breaking contract exercise 1  ****/
                .price(price)
                .build();
    }

}
