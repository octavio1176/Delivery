package com.example.security.domain.orderItem;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity,
        BigDecimal subtotal
) {}