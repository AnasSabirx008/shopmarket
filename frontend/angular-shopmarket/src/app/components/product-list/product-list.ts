import { Component, computed, OnInit, signal } from '@angular/core';
import { CurrencyPipe, NgFor, NgIf } from '@angular/common';
import { Product } from '../../common/product';
import { ProductService } from '../../services/product';

@Component({
  imports: [NgFor, NgIf, CurrencyPipe],
  selector: 'app-product-list',
  styleUrl: './product-list.css',
  templateUrl: './product-list.html',
})
export class ProductList implements OnInit {

  products = signal<Product[]>([]);
  categories = signal<string[]>([]);
  selectedCategory = signal('');
  searchTerm = signal('');
  loading = signal(true);
  loadError = signal<string | null>(null);
  filteredProducts = computed(() => {
    const query = this.searchTerm().trim().toLowerCase();
    if (!query) {
      return this.filterByCategory(this.products());
    }

    return this.filterByCategory(this.products().filter(product =>
      `${product.name} ${product.sku} ${product.description} ${product.categoryName ?? ''}`
        .toLowerCase()
        .includes(query)
    ));
  });

  private filterByCategory(products: Product[]): Product[] {
    const category = this.selectedCategory();
    return category
      ? products.filter(product => product.categoryName === category)
      : products;
  }

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading.set(true);
    this.loadError.set(null);
    this.productService.getProductCatalog().subscribe({
      next: catalog => {
        this.products.set(catalog.products);
        this.categories.set(catalog.categories);
        this.loading.set(false);
      },
      error: (error: unknown) => {
        console.error('Failed to load products and categories', error);
        this.loadError.set('Could not load products. Check that the backend is running and try again.');
        this.loading.set(false);
      },
    });
  }

  updateSearch(event: Event): void {
    if (event.target instanceof HTMLInputElement) {
      this.searchTerm.set(event.target.value);
    }
  }

  updateCategory(event: Event): void {
    if (event.target instanceof HTMLSelectElement) {
      this.selectedCategory.set(event.target.value);
    }
  }

  clearSearch(): void {
    this.searchTerm.set('');
  }

  getProductInitials(name: string): string {
    return name.trim().split(/\s+/).slice(0, 2).map(word => word[0]).join('').toUpperCase();
  }

  onImageError(event: Event): void {
    if (event.target instanceof HTMLImageElement) {
      event.target.hidden = true;
    }
  }

}
