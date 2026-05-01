package com.filipearruda.tech_challenge_restaurant_manager.domain.user;

import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.CreateUserRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.UpdateUserRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuario")
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Setter
    @Column(name = "nome")
    private String name;

    @Setter
    @Column(name = "email", unique = true)
    private String email;

    @Setter
    @Column(name = "login")
    private String login;

    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime lastUpdate;

    @Setter
    @Column(name = "endereco")
    private String address;

    @Setter
    @Column(name = "senha")
    private String password;

    @Column(name = "perfil")
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private Profile profile;

    public User(CreateUserRequest request, String encryptedPassword) {
        this.name = request.name();
        this.email = request.email();
        this.login = request.login();
        this.address = request.address();
        this.password = encryptedPassword;
        this.lastUpdate = LocalDateTime.now();
        this.profile = request.profile();
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.profile.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public void updateUser(UpdateUserRequest request) {
        this.name = request.name() == null ? this.name : request.name();
        this.email = request.email() == null ? this.email : request.email();
        this.login = request.login() == null ? this.login : request.login();
        this.address = request.address() == null ? this.address : request.address();
        this.lastUpdate = LocalDateTime.now();
    }

    public void changeUserPassword(String password) {
        this.lastUpdate = LocalDateTime.now();
        this.password = password;
    }
}
