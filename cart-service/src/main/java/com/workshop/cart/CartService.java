package com.workshop.cart;

import com.workshop.cart.clients.ProductsClient;
import com.workshop.cart.dto.Cart;
import com.workshop.cart.dto.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class CartService {

    CartRepository cartRepository;
    ProductsClient productsClient;

    Cart getCart() {
        System.out.println("==== in getCart ====");
        Cart cart =  cartRepository.getCart();
        cart.setProducts(cart.getProducts().stream().map(this::enrichWithProductDetails).collect(toList()));
        cart.setTotalPrice(cart.getProducts().stream().mapToInt(product -> product.getPrice()).sum());
        return cart;
    }
    Integer addProduct(Product product) {
        System.out.println("==== in addProduct ====");
        Cart cart = cartRepository.getCart();
        cart.getProducts().add(product);
        return cart.getProducts().size();
    }
    Integer clearCart() {
        Cart cart = cartRepository.getCart();
        cart.getProducts().clear();
        return cart.getProducts().size();
    }

    private Product enrichWithProductDetails(Product cartProduct) {
        Optional<Product> product = null;
        try {
            product = productsClient.getProductByRef(cartProduct.getRef());
        }
        catch (RuntimeException e){
            System.out.println("==== ERROR getting product details ==== " + e.getMessage());
        }

        if (!isNull(product)) {
            cartProduct.setName(product.get().getName());
           // cartProduct.setShortName(product.get().getShortName());  /***** breaking contract exercise 1  ****/
            cartProduct.setPrice(product.get().getPrice());
        }
        return cartProduct;
    }
}
