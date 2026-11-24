import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../common/product';
import { forkJoin, map, Observable } from 'rxjs';

export interface ProductCatalog {
  products: Product[];
  categories: string[];
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private readonly baseUrl = 'http://localhost:8080/api';

  constructor(private httpClient: HttpClient) { }

  getProductCatalog(): Observable<ProductCatalog> {
    return forkJoin({
      products: this.httpClient.get<PageResponse<Product>>(`${this.baseUrl}/products?size=100`),
      categories: this.httpClient.get<ProductCategory[]>(`${this.baseUrl}/product-categories`),
    }).pipe(
      map(({ products, categories }) => ({
        products: products.content,
        categories: categories.map(category => category.categoryName),
      }))
    );
  }
}

interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

interface ProductCategory {
  id: number;
  categoryName: string;
}