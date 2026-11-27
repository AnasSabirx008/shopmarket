package com.shopmarket.spring_boot_shopmarket.order.service;

import com.shopmarket.spring_boot_shopmarket.common.exception.BusinessConflictException;
import com.shopmarket.spring_boot_shopmarket.common.exception.InvalidRequestException;
import com.shopmarket.spring_boot_shopmarket.order.dao.OrderRepository;
import com.shopmarket.spring_boot_shopmarket.order.dto.CreateOrderRequest;
import com.shopmarket.spring_boot_shopmarket.order.dto.OrderDto;
import com.shopmarket.spring_boot_shopmarket.order.dto.OrderItemRequest;
import com.shopmarket.spring_boot_shopmarket.order.entity.Order;
import com.shopmarket.spring_boot_shopmarket.order.service.OrderService;
import com.shopmarket.spring_boot_shopmarket.product.dto.ProductDto;
import com.shopmarket.spring_boot_shopmarket.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private ProductService productService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        productService = mock(ProductService.class);
        orderService = new OrderService(orderRepository, productService);
    }

    @Test
    void placeOrder_validCart_usesServerPrices() throws BusinessConflictException, InvalidRequestException {
        when(productService.findAllByIds(any())).thenReturn(List.of(product(1L, "29.99", 10, true)));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        OrderDto dto = orderService.placeOrder("user-1",
                new CreateOrderRequest(List.of(new OrderItemRequest(1L, 2))));

        assertThat(dto.status()).isEqualTo("PENDING");
        assertThat(dto.totalPrice()).isEqualByComparingTo("59.98");
        assertThat(dto.items()).hasSize(1);
        verify(orderRepository).save(any(Order.class));
    }

    private static ProductDto product(Long id, String price, int stock, boolean active) {
        return new ProductDto(id, "SKU-" + id, "Product " + id, null, new BigDecimal(price),
                null, active, stock, null, null, 1L, "Books");
    }
}