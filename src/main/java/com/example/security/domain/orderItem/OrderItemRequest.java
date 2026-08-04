package com.example.security.domain.orderItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @NotNull Long productId,
        @Positive Integer quantity
) {}