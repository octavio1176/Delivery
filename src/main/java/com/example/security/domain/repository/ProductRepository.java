package com.example.security.domain.repository;

import com.example.security.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long > {
    Optional<Product> findByName(String name);

    Optional<Product> deleteProductByName(String name);
    Optional<List<Product>> findAllByName(String name);
}