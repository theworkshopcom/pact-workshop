package com.workshop.cart.clients;

import com.workshop.cart.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "productsclient", url = "${productsclient.url}")
public interface ProductsClient {
    @GetMapping("/api/products/product")
    Optional<Product> getProductByRef(@RequestParam String ref);
}
