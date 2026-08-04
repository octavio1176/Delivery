package com.example.security.mapper;

import com.example.security.domain.OrderDto.OrderResponse;
import com.example.security.domain.entity.Order;
import com.example.security.domain.entity.OrderItem;
import com.example.security.domain.orderItem.OrderItemResponse;
import java.math.BigDecimal;
import java.util.List;

public class OrderMapper {

    public static OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getPrice(),
                item.getQuantity(),
                item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
        );
    }

    public static OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getOrderItems().stream()
                .map(OrderMapper::toItemResponse)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUser().getEmail(),
                order.getOrderStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                items
        );
    }

    public static List<OrderResponse> toResponseList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toResponse)
                .toList();
    }
}
