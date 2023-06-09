package com.workshop.cart;

import com.workshop.cart.dto.Cart;
import com.workshop.cart.dto.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class CartRepository {
    private Cart cart = new Cart(new ArrayList<Product>(), 0);

    public Cart getCart() {
        return cart;
    }

}
