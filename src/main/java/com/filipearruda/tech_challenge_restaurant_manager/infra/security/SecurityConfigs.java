package com.filipearruda.tech_challenge_restaurant_manager.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain techChallengeSecurityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(
                        req -> {
                            req.requestMatchers(HttpMethod.POST, "/v1/user").permitAll();
                            req.requestMatchers(HttpMethod.POST, "/v1/user/change-password").permitAll();
                            req.requestMatchers(HttpMethod.POST, "/v1/user/valid").permitAll();
                            req.requestMatchers(HttpMethod.PATCH, "/v1/user/{id}").permitAll();
                            req.requestMatchers(HttpMethod.GET, "/v1/user").permitAll();
                            req.requestMatchers(HttpMethod.GET, "/**").permitAll();

                            req.anyRequest().authenticated();
                        }
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
