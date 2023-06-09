package com.workshop.products.contracts;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.workshop.products.ProductService;
import com.workshop.products.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static com.workshop.products.TestUtils.PRODUCT_REF;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
/******** Exercise 1 - provider & pactbroker **************/
@Provider("products-service")
@PactBroker
//@PactFolder("pacts")
/***********************************/

@ActiveProfiles("test")
public class ProviderContractTest {
    @LocalServerPort
    private int serverPort;

    /******** Exercise 1 - mock service layer **************/
    @MockBean
    ProductService productService;


    /******** Exercise 1 - test template **************/
    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        if(context != null) {
            context.verifyInteraction();
        }
    }


    /******** Exercise 1 - httpTarget **************/
    @BeforeEach
    void setTarget(PactVerificationContext context) {
        if(context != null) {
            context.setTarget(new HttpTestTarget("localhost", serverPort));
        }
    }


    /******** Exercise 1 -state **************/
    @State("a product exists")
    public void productExist() {
        when(productService.getProduct(any()))
                .thenReturn(TestUtils.getProduct(PRODUCT_REF, "contract_product", "first contract product", 200));
    }


    /******** Exercise 1 -state **************/
    @State("product does not exists")
    public void productNotFound() {
        when(productService.getProduct(any()))
                .thenReturn(null);
    }


    /******** Exercise 2 -state **************/
    @State("The user is visiting the home page")
    public void getAllProducts() {
        when(productService.getAllProducts())
                .thenReturn(TestUtils.getListProductsForFrontend());
    }

}
