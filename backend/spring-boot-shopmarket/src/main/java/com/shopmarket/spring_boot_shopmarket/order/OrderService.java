package com.shopmarket.spring_boot_shopmarket.order.service;

import com.shopmarket.spring_boot_shopmarket.order.dao.OrderRepository;
import com.shopmarket.spring_boot_shopmarket.order.dto.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDto> findMyOrders(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(OrderDto::from)
                .toList();
    }
}