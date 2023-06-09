package com.workshop.products;

import static com.workshop.products.TestUtils.PRODUCT_REF;
import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.workshop.products.dto.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static List<Product> listProducts;

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeAll
    public static void setup() {
        listProducts = TestUtils.getListProducts();
    }

    @Test
    void getAllProductSuccess() {
        when(productRepository.getAllProducts()).thenReturn(listProducts);
        List<Product> products = productService.getAllProducts();

        assertThat(products.size()).isEqualTo(listProducts.size());
    }

    @Test
    void getProductSuccess() {
        when(productRepository.getAllProducts()).thenReturn(listProducts);
        Product product = productService.getProduct(PRODUCT_REF);

        assertThat(product.getRef()).isEqualTo(PRODUCT_REF);
    }

    @Test
    void getProductFailed() {
        Product product = productService.getProduct("wrong");

        assertThat(isNull(product));
    }



}
