package com.example.security.domain.repository;

import com.example.security.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean findByEmail(String email);

    Optional<User> findByConfirmationToken(String confirmationToken);


    Optional<User> findUsersByEmail(String email);
}