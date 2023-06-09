package com.workshop.products;

import com.workshop.products.dto.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {
    ProductService productService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {}

    @GetMapping("/api/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/api/products/product")
    public Product getProduct(@RequestParam String ref) {
        Product product = productService.getProduct(ref);
        if (isNull(product)) {
            throw new ResourceNotFoundException();
        } else {
            return product;
        }
    }
}
