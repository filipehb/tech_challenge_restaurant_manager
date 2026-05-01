package com.filipearruda.tech_challenge_restaurant_manager.config.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
