package com.example.security.domain.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;
public record ResetPasswordRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String code,
        @NotBlank
        @Size(min = 8)
        String newPassword
) {}