package com.example.security.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank String name,
        @Positive BigDecimal price,
        @PositiveOrZero Integer quantity
) {}