package com.example.security.controller;

import com.example.security.domain.OrderDto.OrderRequest;
import com.example.security.domain.OrderDto.OrderResponse;
import com.example.security.domain.entity.User;
import com.example.security.service.OrderService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("API")
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("order")
    public ResponseEntity<OrderResponse> makeOrder(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody OrderRequest request
    ) throws MessagingException
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(user, request));
    }
}
