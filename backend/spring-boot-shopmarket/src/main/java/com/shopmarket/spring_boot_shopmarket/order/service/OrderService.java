package com.shopmarket.spring_boot_shopmarket.order.service;

import com.shopmarket.spring_boot_shopmarket.common.exception.BusinessConflictException;
import com.shopmarket.spring_boot_shopmarket.common.exception.InvalidRequestException;
import com.shopmarket.spring_boot_shopmarket.order.dao.OrderRepository;
import com.shopmarket.spring_boot_shopmarket.order.dto.CreateOrderRequest;
import com.shopmarket.spring_boot_shopmarket.order.dto.OrderDto;
import com.shopmarket.spring_boot_shopmarket.order.dto.OrderItemRequest;
import com.shopmarket.spring_boot_shopmarket.order.entity.Order;
import com.shopmarket.spring_boot_shopmarket.order.entity.OrderItem;
import com.shopmarket.spring_boot_shopmarket.order.entity.OrderStatus;
import com.shopmarket.spring_boot_shopmarket.product.dto.ProductDto;
import com.shopmarket.spring_boot_shopmarket.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public OrderDto placeOrder(String userId, CreateOrderRequest createOrderRequest) throws InvalidRequestException, BusinessConflictException {
        //if duplicate
        Set<Long> productIds = new HashSet<>();
        for (OrderItemRequest line : createOrderRequest.items()){
            if (!productIds.add(line.productId())){ throw new InvalidRequestException("Product " + line.productId() + " is listed twice");}
        }

        //
        Map<Long,ProductDto> products = new HashMap<>();
        for (ProductDto p : productService.findAllByIds(productIds)){
            products.put(p.id(),p);
        }

        //
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);
        BigDecimal total = BigDecimal.ZERO;

        //
        for (OrderItemRequest line : createOrderRequest.items()){
            ProductDto product = products.get(line.productId());
            if (product==null|!product.active()){
                throw new BusinessConflictException("Product " + line.productId() + " is not available");
            }
            if (product.unitsInStock() < line.quantity()){
                throw new BusinessConflictException("Not enough stock for " + line.productId());
            }

            OrderItem item = new OrderItem();
            item.setProductId(product.id());
            item.setProductName(product.name());
            item.setUnitPrice(product.unitPrice());
            item.setQuantity(line.quantity());
            order.addItem(item);

            total = total.add(product.unitPrice().multiply(BigDecimal.valueOf(line.quantity())));

        }

        order.setTotalPrice(total.setScale(2, RoundingMode.HALF_UP));
        Order saved = orderRepository.save(order);
        return OrderDto.from(saved);

    }

    public List<OrderDto> findMyOrders(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(OrderDto::from)
                .toList();
    }
}