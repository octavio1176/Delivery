package com.example.security.domain.repository;

import com.example.security.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdeRepository extends JpaRepository<Order,Long> {
}
