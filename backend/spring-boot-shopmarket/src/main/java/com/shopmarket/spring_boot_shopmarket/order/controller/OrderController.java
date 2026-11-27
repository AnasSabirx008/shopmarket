package com.shopmarket.spring_boot_shopmarket.order.controller;

import com.shopmarket.spring_boot_shopmarket.common.exception.BusinessConflictException;
import com.shopmarket.spring_boot_shopmarket.common.exception.InvalidRequestException;
import com.shopmarket.spring_boot_shopmarket.order.dto.CreateOrderRequest;
import com.shopmarket.spring_boot_shopmarket.order.dto.OrderDto;
import com.shopmarket.spring_boot_shopmarket.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET /api/orders/me : the logged-in user's orders
    @GetMapping("/me")
    public List<OrderDto> myOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.findMyOrders(jwt.getSubject());
    }

    @PostMapping
    public ResponseEntity<OrderDto> placeOrder(@AuthenticationPrincipal Jwt jwt,
                                               @Valid @RequestBody CreateOrderRequest request) throws BusinessConflictException, InvalidRequestException {
        OrderDto order = orderService.placeOrder(jwt.getSubject(), request);
        URI location = URI.create("/api/orders/" + order.id());
        return ResponseEntity.created(location).body(order);
    }
}