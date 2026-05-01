package com.filipearruda.tech_challenge_restaurant_manager.domain.user.response;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        String login,
        LocalDateTime lastUpdate,
        String address
) {
}
