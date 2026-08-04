package com.example.security.domain.OrderDto;

import com.example.security.domain.orderItem.OrderItemRequest;
import jakarta.validation.constraints.NotEmpty;


import java.util.List;

public record OrderRequest(
        @NotEmpty List<OrderItemRequest> items
) {}
