package com.filipearruda.tech_challenge_restaurant_manager.domain.user;

import com.filipearruda.tech_challenge_restaurant_manager.config.exception.ValidationException;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.ChangeUserPasswordRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.CreateUserRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.UpdateUserRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.UserLoginRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public void changeUserPassword(ChangeUserPasswordRequest changeUserPasswordRequest) {
        User user = repository.findByEmail(changeUserPasswordRequest.email()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(changeUserPasswordRequest.password(), user.getPassword())) {
            throw new ValidationException("Old Password is not correct");
        }

        user.changeUserPassword(passwordEncoder.encode(changeUserPasswordRequest.newPassword()));
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = new User(request, passwordEncoder.encode(request.password()));
        User saved = repository.save(user);
        return new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getLogin(),
                saved.getLastUpdate(),
                saved.getAddress()
        );
    }

    @Transactional
    public void updateUser(Long id, UpdateUserRequest request) {
        User user = repository.getReferenceById(id);
        user.updateUser(request);
    }

    @Transactional
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public List<UserResponse> findUserByName(String name) {
        List<User> userByNameIgnoreCase = repository.findUserByNameIgnoreCase(name);
        return userByNameIgnoreCase.stream().map(user ->
                        new UserResponse(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getLogin(),
                                user.getLastUpdate(),
                                user.getAddress())
                )
                .toList();
    }

    public void isLoginValid(UserLoginRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        authenticationManager.authenticate(authenticationToken);
    }
}
