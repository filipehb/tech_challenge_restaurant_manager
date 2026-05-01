package com.filipearruda.tech_challenge_restaurant_manager.domain.user.request;

import com.filipearruda.tech_challenge_restaurant_manager.domain.user.Profile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank(message = "Nome é obrigatório.")
        String name,
        @Email(message = "Você deve fornecer um email valido")
        String email,
        @NotBlank(message = "Login é obrigatório.")
        String login,
        @NotBlank(message = "Senha é obrigatório.")
        String password,
        @NotBlank(message = "Endereço é obrigatório.")
        String address,
        @NotNull(message = "Deve ter um perfil de usuário")
        Profile profile
) {
}
