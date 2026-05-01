package com.filipearruda.tech_challenge_restaurant_manager.domain.user.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserPasswordRequest(
        @NotBlank(message = "Email é obrigatório.")
        String email,
        @NotBlank(message = "Senha é obrigatório.")
        String password,
        @NotBlank(message = "A nova senha é obrigatória.")
        String newPassword
) {
}
