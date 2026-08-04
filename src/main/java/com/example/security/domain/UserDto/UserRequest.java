package com.example.security.domain.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record  UserRequest(

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, max = 16, message = "A senha deve ter entre 8 e 16 caracteres")
        String password
) {
}
