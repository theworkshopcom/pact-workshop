import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from './Product';
import { Cart } from './Cart';
import { mergeMap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {

  constructor(private http: HttpClient) { }

  getCart() {
    return this.http.get<Cart>('http://127.0.0.1:9002/api/cart');
  }

  updateCart(product: Product) {
    return this.http.post<number>('http://127.0.0.1:9002/api/cart/product', product).pipe(mergeMap(() => this.getCart()));
  }

  clearCart() {
    return this.http.put<number>('http://127.0.0.1:9002/api/cart/clear', undefined).pipe(mergeMap(() => this.getCart()));
  }
}