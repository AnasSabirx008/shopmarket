package com.shopmarket.spring_boot_shopmarket.controller;

import com.shopmarket.spring_boot_shopmarket.dto.PageResponse;
import com.shopmarket.spring_boot_shopmarket.dto.ProductDto;
import com.shopmarket.spring_boot_shopmarket.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products?page=0&size=20&categoryId=1
    @GetMapping
    public PageResponse<ProductDto> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return productService.findAll(categoryId, page, size);
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }
}