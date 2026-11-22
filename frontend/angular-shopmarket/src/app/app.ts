import { Component } from '@angular/core';
import { ProductList } from './components/product-list/product-list';

@Component({
  imports: [ProductList],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
}
