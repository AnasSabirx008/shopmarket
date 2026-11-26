package com.shopmarket.spring_boot_shopmarket.order.dto;

import com.shopmarket.spring_boot_shopmarket.order.entity.Order;
import com.shopmarket.spring_boot_shopmarket.order.entity.OrderItem;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public record OrderDto(Long id, String status, BigDecimal totalPrice, Instant createdAt, List<OrderItemDto> items) {

    public static OrderDto from(Order o) {
        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItem item : o.getItems()) {
            OrderItemDto dto = OrderItemDto.from(item);
            itemDtos.add(dto);
        }
        return new OrderDto(o.getId(), o.getStatus().name(), o.getTotalPrice(), o.getCreatedAt(), itemDtos);
    }
}