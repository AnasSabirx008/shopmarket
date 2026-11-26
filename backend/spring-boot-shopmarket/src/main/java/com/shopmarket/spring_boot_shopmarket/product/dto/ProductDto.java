package com.shopmarket.spring_boot_shopmarket.product.dto;

import com.shopmarket.spring_boot_shopmarket.product.entity.Product;

import java.math.BigDecimal;
import java.util.Date;

public record ProductDto(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal unitPrice,
        String imageUrl,
        boolean active,
        int unitsInStock,
        Date dateCreated,
        Date lastUpdated,
        Long categoryId,
        String categoryName
) {public static ProductDto from(Product p) {
    return new ProductDto(
            p.getId(), p.getSku(), p.getName(), p.getDescription(),
            p.getUnitPrice(), p.getImageUrl(), p.isActive(), p.getUnitsInStock(),
            p.getDateCreated(), p.getLastUpdated(),
            p.getCategory() != null ? p.getCategory().getId() : null,
            p.getCategory() != null ? p.getCategory().getCategoryName() : null
    );
}
}
