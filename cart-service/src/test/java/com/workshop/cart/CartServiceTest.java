package com.workshop.cart;

import com.workshop.cart.clients.ProductsClient;
import com.workshop.cart.dto.Cart;
import com.workshop.cart.dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    private static Cart cartStored;

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductsClient productsClient;

    @BeforeEach
    public void setup() {
        cartStored = TestUtils.getTestCart();
    }

    @Test
    void getCartSuccess() {
        when(cartRepository.getCart()).thenReturn(cartStored);
        when(productsClient.getProductByRef(TestUtils.PRODUCT_REF_1)).thenReturn(Optional.of(TestUtils.PRODUCT_1_WITH_DETAILS));
        when(productsClient.getProductByRef(TestUtils.PRODUCT_REF_2)).thenReturn(Optional.of(TestUtils.PRODUCT_2_WITH_DETAILS));

        Cart cart = cartService.getCart();

        assertThat(cart).isEqualTo(TestUtils.getExpectedCartWithDetails());
    }

    @Test
    void addCartSuccess() {
        when(cartRepository.getCart()).thenReturn(cartStored);
        Integer numProducts = cartService.addProduct(
                Product.builder()
                        .ref(TestUtils.PRODUCT_REF_3)
                        .build());

        assertThat(numProducts).isEqualTo(3);
    }


    @Test
    void clearCartSuccess() {
        when(cartRepository.getCart()).thenReturn(cartStored);
        Integer numProducts = cartService.clearCart();

        assertThat(numProducts).isEqualTo(0);
    }

}
