package com.filipearruda.tech_challenge_restaurant_manager.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserByNameIgnoreCase(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
