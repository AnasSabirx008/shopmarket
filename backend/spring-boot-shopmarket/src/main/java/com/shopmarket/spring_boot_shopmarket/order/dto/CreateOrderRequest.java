package com.shopmarket.spring_boot_shopmarket.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderRequest(@NotEmpty @Size(max = 20) @Valid List<OrderItemRequest> items) {
}