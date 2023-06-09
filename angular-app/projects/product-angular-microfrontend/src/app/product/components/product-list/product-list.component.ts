import { Component, OnInit } from '@angular/core';
import { ProductService } from './product.service';
import { Product } from './Product';
import { Observable } from 'rxjs';
import { ProductAdded } from './ProductAdded';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
  providers: [
    ProductService,
    { provide: Window, useValue: window }
  ]
})
export class ProductListComponent {
  products$: Observable<Product[]>;

  constructor(private productService: ProductService, private window: Window) { 
    this.products$ = this.productService.getProducts();
  }
  onClickAddToCart(product: Product) {
    this.window.dispatchEvent(new CustomEvent<ProductAdded>('productAdded', {
      detail: {
        product,
        quantity: 1
      }
    }))
  }
}
