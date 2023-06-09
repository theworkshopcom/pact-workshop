import { Product } from "./Product";

export interface ProductAdded {
    product: Product;
    quantity: number;
}