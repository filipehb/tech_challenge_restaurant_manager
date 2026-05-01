package com.filipearruda.tech_challenge_restaurant_manager.domain.user.request;

public record UpdateUserRequest(
        String name,
        String email,
        String login,
        String address
) {
}
