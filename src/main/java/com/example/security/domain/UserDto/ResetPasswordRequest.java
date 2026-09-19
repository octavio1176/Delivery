package com.example.security.domain.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;
public record ResetPasswordRequest(
        @Email(message = "E-mail inválido")
        @NotBlank
        String email,
        @NotBlank(message = "o codigo nao deve ser vazio ")
        String code,
        @NotBlank( message = " a nova senha nao deve estar vazia ")
        @Size(min = 8)
        String newPassword
) {}