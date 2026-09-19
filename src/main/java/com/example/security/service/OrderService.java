package com.example.security.service;

import com.example.security.domain.OrderDto.OrderRequest;
import com.example.security.domain.OrderDto.OrderResponse;
import com.example.security.domain.entity.*;
import com.example.security.domain.orderItem.OrderItemRequest;
import com.example.security.domain.repository.OrderRepository;
import com.example.security.domain.repository.ProductRepository;
import com.example.security.exception.InsufficientStock;
import com.example.security.mapper.OrderMapper;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;

    @Transactional
    public OrderResponse createOrder(User user, OrderRequest request) throws MessagingException {

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemDto : request.items())
        {

            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + itemDto.productId()));

            if (product.getQuantity() < itemDto.quantity())
            {
                throw new InsufficientStock();
            }

            product.setQuantity(product.getQuantity() - itemDto.quantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setPrice(product.getPrice());

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemDto.quantity())));
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotal(total);
        emailService.sendOrderConfirmationEmail(user.getEmail(), order);

        Order saved = orderRepository.save(order);
        return OrderMapper.toResponse(saved);

    }

    public OrderResponse findById(Long id)
    {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not  found "));
        return OrderMapper.toResponse(order);
    }

    public List<OrderResponse> findByUser(User user)
    {
        return OrderMapper.toResponseList(orderRepository.findByUser(user));
    }
}