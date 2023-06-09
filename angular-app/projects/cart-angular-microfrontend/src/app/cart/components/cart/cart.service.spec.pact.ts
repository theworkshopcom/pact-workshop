import { TestBed } from '@angular/core/testing';
import { CartService } from './cart.service';
import { Matchers, MessageConsumerPact, asynchronousBodyHandler } from '@pact-foundation/pact';
import * as path from 'path';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, lastValueFrom } from 'rxjs';
import { Cart } from './Cart';
import { Product } from './Product';

describe('Cart Service', () => {
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let cartService: CartService;
  const messagePact = new MessageConsumerPact({
    consumer: "cart-angular-microfrontend",
    dir: path.resolve(process.cwd(), "pacts"),
    pactfileWriteMode: 'overwrite',
    provider: "product-angular-microfrontend",
    logLevel: "info",
  })

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        CartService
      ]
    });

    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    cartService = TestBed.inject(CartService);
  });

  it('should update cart', () => {
    messagePact
      .given("a Cart app listening for a productAdded event")
      .expectsToReceive("a productAdded event")
      .withContent({
        ref: Matchers.like("111"),
        name: Matchers.like("Agile Testing"),
        description: Matchers.like("A Practical Guide for Testers and Agile Teams"),
        price: Matchers.like(5)
    }).withMetadata({ eventName: "productAdded" }).verify((product) => firstValueFrom(cartService.updateCart(product as any as Product)) as any as Promise<Cart>);
    // cartService.updateCart({"ref":"111","name":"Agile Testing","description":"A Practical Guide for Testers and Agile Teams","price":5}).subscribe((res) => {
    //     expect(res).toEqual({"ref":"111","name":"Agile Testing","description":"A Practical Guide for Testers and Agile Teams","price":5});
    // });
    const req = httpTestingController.expectOne('http://127.0.0.1:9002/api/cart/product');
    expect(req.request.method).toEqual('POST');
    
    req.flush(1);
    const req2 = httpTestingController.expectOne('http://127.0.0.1:9002/api/cart');
    expect(req2.request.method).toEqual('GET');
    req2.flush([
        {"ref":"111","name":"Agile Testing","description":"A Practical Guide for Testers and Agile Teams","price":5}
    ]);
  });

  afterEach(() => {
    httpTestingController.verify();
  });
});