package com.workshop.products;

import com.workshop.products.dto.Product;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepository {
    public List<Product> getAllProducts() {
        Product product_1 = buildProduct("111", "Libro - Agile Testing",
                "A Practical Guide for Testers and Agile Teams", 5);
        Product product_2 = buildProduct("222", "printer",
                "Laser colour printer", 10);
        Product product_3 = buildProduct("333", "Laptop",
                "15 pulgadas laptop", 15);
        Product product_4 = buildProduct("444", "Mouse",
                "White mouse", 8);
        Product product_5 = buildProduct("555", "Keyboard",
                "White keyboard", 9);

        return ImmutableList.of(product_1, product_2, product_3, product_4, product_5);
    }

    private Product buildProduct(String ref, String name, String description, int price) {
        return  Product.builder()
                .ref(ref)
                .name(name)
                //.shortName(name)           /****** Breaking contract demo *****/
                //.description(description)  /****** Breaking contract demo *****/
                .price(price)
                .build();
    }


}
