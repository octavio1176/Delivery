package com.example.security.domain.LoginDto;
import java.time.LocalDateTime;

public record VerificationCode(String code, LocalDateTime expiresAt) {
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}