import { TestBed } from '@angular/core/testing';
import { ProductService } from './product.service';
import { Pact, InteractionObject, MessageProviderPact, providerWithMetadata } from '@pact-foundation/pact';
import * as path from 'path';
import { HttpClientModule } from '@angular/common/http';

describe('Product Service', () => {
  describe('Consumer tests', () => {
    let provider: Pact;
    const productsResponse = [
      {"ref":"111","name":"Agile Testing","price":5},
    ];
    const interaction: InteractionObject = {
      state: 'The user is visiting the home page',
      uponReceiving: 'a request to get all products',
      withRequest: {
        path: '/api/products',
        method: 'GET'
      },
      willRespondWith: {
        body: productsResponse,
        status: 200,
        headers: {
          'Content-Type': 'application/json',
        },
      },
    };

    beforeEach(async () => {
      provider = new Pact({
        port: 9001,
        log: path.resolve(process.cwd(), 'pacts', 'logs', 'pact.log'),
        dir: path.resolve(process.cwd(), 'pacts'),
        spec: 3,
        logLevel: 'info',
        consumer: 'product-angular-microfrontend',
        provider: 'products-service'
      });
      await provider.setup();

      await provider.addInteraction(interaction);

      await TestBed.configureTestingModule({
        imports: [
          HttpClientModule
        ],
        providers: [
          ProductService
        ]
      });
    });

    it('should get products', async () => {
      const productService: ProductService = TestBed.inject(ProductService);
      const response = await productService.getProducts().toPromise();
      console.log(response);
      expect(response).toEqual(productsResponse);
    });

    afterEach(async () => {
      await provider.verify();
    });

    afterAll(async () => {
      await provider.finalize();
    });
  });
  
  describe('Provider tests', () => {
    const messagePact = new MessageProviderPact({
        messageProviders: {
            'a productAdded event': providerWithMetadata(() => new Promise((resolve) => resolve({
              ref:'Different Ref',name:'Different Name',description:'Different Description',price:6
            })), {
              eventName: 'productAdded',
            }),
        },
        provider: "product-angular-microfrontend",
        providerVersion: "1.0.0",
        consumerVersionSelectors: [{ "branch": "main" }],
        pactBrokerUrl: "http://pact-broker:9292",
        publishVerificationResult: true,
        logLevel: 'info'
    });

    it("should provide Product the consumer expect", async () => {
        return messagePact.verify();
    });
  });
});