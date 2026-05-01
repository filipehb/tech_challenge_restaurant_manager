package com.filipearruda.tech_challenge_restaurant_manager.domain.user.request;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequest(
        @NotNull(message = "Email é obrigatório.")
        String email,
        @NotNull(message = "Senha é obrigatório.")
        String password
) {
}
