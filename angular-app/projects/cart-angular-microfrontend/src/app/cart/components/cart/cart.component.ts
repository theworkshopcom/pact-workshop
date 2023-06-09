import { Component } from '@angular/core';
import { CartService } from './cart.service';
import { ProductAdded } from './ProductAdded';
import { Observable } from 'rxjs';
import { Cart } from './Cart';

declare global {
  interface WindowEventMap {
    'productAdded': CustomEvent<ProductAdded>;
  }
}

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  providers: [
    CartService,
    { provide: Window, useValue: window }
  ]
})
export class CartComponent {
  public cart$: Observable<Cart>;

  constructor(private window: Window, private cartService: CartService) {
    this.window.addEventListener('productAdded', (event) => {
      this.cart$ = this.cartService.updateCart(event.detail.product);
    });
    this.cart$ = this.cartService.getCart();
  }

  onClickClearCart() {
    this.cart$ = this.cartService.clearCart();
  }
}
