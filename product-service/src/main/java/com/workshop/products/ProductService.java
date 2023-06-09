package com.workshop.products;

import com.workshop.products.dto.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductService {

    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProduct(@RequestParam String ref) {
        System.out.println("==== in getProduct ====");
        List<Product> allProducts = productRepository.getAllProducts();
        allProducts =  allProducts.stream().filter(product-> product.getRef().equals(ref)).collect(Collectors.toList());
        return allProducts.isEmpty() ? null : allProducts.get(0);
    }
}
