package com.shopmarket.spring_boot_shopmarket.order.dto;

import com.shopmarket.spring_boot_shopmarket.order.entity.OrderItem;
import java.math.BigDecimal;

public record OrderItemDto(Long productId, String productName, BigDecimal unitPrice, int quantity, BigDecimal subtotal) {

    public static OrderItemDto from(OrderItem i) {
        return new OrderItemDto(i.getProductId(), i.getProductName(), i.getUnitPrice(), i.getQuantity(),
                i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
    }
}