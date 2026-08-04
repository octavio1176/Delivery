package com.example.security.domain.repository;

import com.example.security.domain.entity.Order;
import com.example.security.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
