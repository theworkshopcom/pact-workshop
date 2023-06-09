package com.workshop.cart.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Cart {
    List<Product> products;
    int totalPrice;
}
