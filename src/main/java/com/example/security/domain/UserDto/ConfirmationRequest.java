package com.example.security.domain.UserDto;
public record ConfirmationRequest(
        String email,
        String code
) {}