package com.shopmarket.spring_boot_shopmarket.controller;

import com.shopmarket.spring_boot_shopmarket.dto.ProductCategoryDto;
import com.shopmarket.spring_boot_shopmarket.service.ProductCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    public ProductCategoryController(ProductCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<ProductCategoryDto> getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ProductCategoryDto getCategory(@PathVariable Long id) {
        return categoryService.findById(id);
    }
}