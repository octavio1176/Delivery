package com.example.security.domain.OrderDto;

import com.example.security.domain.entity.OrderStatus;
import com.example.security.domain.orderItem.OrderItemResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String userEmail,
        OrderStatus status,
        BigDecimal total,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {}
