package com.workshop.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder

public class Product {
    String ref;
    String name;
  // String shortName;     /****** Breaking contract demo *****/
  // String description;   /****** Breaking contract demo *****/
    int price;
}


