package com.filipearruda.tech_challenge_restaurant_manager.domain.user;

import com.filipearruda.tech_challenge_restaurant_manager.config.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserByEmail((username))
                .orElseThrow(() -> new ValidationException("User not found!"));
    }
}
