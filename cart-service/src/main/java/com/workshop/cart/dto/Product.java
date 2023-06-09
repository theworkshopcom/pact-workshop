package com.workshop.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Product {
    String ref;
    String name;
   // String shortName;  /***** breaking contract exercise 1  ****/
    int price;
}
