package com.shopmarket.spring_boot_shopmarket.product.dto;

import com.shopmarket.spring_boot_shopmarket.product.entity.ProductCategory;

public record ProductCategoryDto(Long id, String categoryName) {
    public static ProductCategoryDto from(ProductCategory c){
        return new ProductCategoryDto(c.getId(),c.getCategoryName());
    }
}
