package com.shopmarket.spring_boot_shopmarket.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(@NotNull Long productId , @Min(0) @Max(20) int quantity) {
}
