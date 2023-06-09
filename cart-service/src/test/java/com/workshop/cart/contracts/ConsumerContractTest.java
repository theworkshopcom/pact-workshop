package com.workshop.cart.contracts;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.workshop.cart.TestUtils;
import com.workshop.cart.clients.ProductsClient;
import com.workshop.cart.dto.Product;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest
/******** Exercise 1 - provider setup **************/
@PactTestFor(providerName = "products-service", port="36000")
/***************************************************/
@ActiveProfiles("test")
public class ConsumerContractTest {

    @Autowired
    ProductsClient productsClient;

    /******** Exercise 1 - @Pact **************/
    @Pact(consumer = "cart-service") //1 - Pact Annotation
    public RequestResponsePact getProductSuccess(PactDslWithProvider builder) throws IOException {
        PactDslJsonBody body_response = new PactDslJsonBody();
        body_response.stringType("name", "book");
      //  body_response.stringType("shortName", "book");  /// breaking contract exercise 1
        body_response.numberType("price", 100);
        return builder
                .given("a product exists") //4 - Provider state from consumer's perspective
                .uponReceiving("request to get an existing product") //2 - name of request
                    .path("/api/products/product")
                    .method(GET.name())
                    .matchQuery("ref", ".*", "123")
                .willRespondWith() //3 expected response
                    .body(body_response)
                    .status(200)
                .toPact();
    }

    /******** Exercise 1 - @Test **************/
    @Test
    @PactTestFor(pactMethod = "getProductSuccess")
    void testGetProductSuccess() {
        Optional<Product> productReceived = productsClient.getProductByRef("123");

        assertThat(productReceived).isPresent();
        assertThat(productReceived.get()).isEqualTo(TestExpectations.product);
    }

    /******** Exercise 1 - @Pact **************/
     @Pact(consumer = "cart-service") //1 - Pact Annotation
    public RequestResponsePact getProductNotFound(PactDslWithProvider builder) {
        return builder
                .given("product does not exists") //4 - Provider state from consumer's perspective
                .uponReceiving("request to get a not existing product") //2 - name of request
                   .path("/api/products/product")
                    .method(GET.name())
                  .matchQuery("ref", ".*", "xxx")
                .willRespondWith() //3 expected response
                   .status(404)
                .toPact();
    }

    /******** Exercise 1 - @Test **************/
    @Test
    @PactTestFor(pactMethod = "getProductNotFound")
    void testGetProductNotFound() {
        assertThatExceptionOfType(FeignException.NotFound.class).isThrownBy(() ->
                productsClient.getProductByRef("xxx")).withMessageContaining("[404 Not Found] during [GET]");
    }

    /******** Exercise 1 - @Pact **************/
    @Pact(consumer = "cart-service") //1 - Pact Annotation
    public RequestResponsePact getProductBadRequest(PactDslWithProvider builder) {
        return builder
                 // No Given needed, as is the default behavior when parameters are not send
                .uponReceiving("request to get a not existing product") //2 - name of request
                    .path("/api/products/product")
                    .method(GET.name())
                .willRespondWith() //3 expected response
                    .status(400)
                .toPact();
    }

    /******** Exercise 1 - @Test **************/
    @Test
    @PactTestFor(pactMethod = "getProductBadRequest")
    void testGetProductBadRequest() {
            assertThatExceptionOfType(FeignException.BadRequest.class).isThrownBy(() ->
                    productsClient.getProductByRef(null)).withMessageContaining("[400 Bad Request] during [GET]");
    }



    interface TestExpectations {
        Product product = TestUtils.getProduct(null, "book", 100);
    }
}
