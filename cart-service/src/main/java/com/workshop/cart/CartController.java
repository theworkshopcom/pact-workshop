package com.workshop.cart;

import com.workshop.cart.dto.Cart;
import com.workshop.cart.dto.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {
    CartService cartService;

    @GetMapping("/api/cart")
    public Cart getCart() {
        return cartService.getCart();
    }

    @PostMapping(path = "/api/cart/product",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer addProduct(@RequestBody Product product) {
        return cartService.addProduct(product);
    }

    @PutMapping("/api/cart/clear")
    public Integer clearCart() {
        return cartService.clearCart();
    }
}
