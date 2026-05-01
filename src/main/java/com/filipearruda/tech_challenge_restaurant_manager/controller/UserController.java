package com.filipearruda.tech_challenge_restaurant_manager.controller;

import com.filipearruda.tech_challenge_restaurant_manager.domain.user.UserService;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.ChangeUserPasswordRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.CreateUserRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.UpdateUserRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.UserLoginRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Tag(name = "Update")
    @Operation(summary = "Change user password")
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangeUserPasswordRequest request) {
        userService.changeUserPassword(request);
        return ResponseEntity.noContent().build();
    }

    @Tag(name = "Validate")
    @Operation(summary = "Validate user login")
    @PostMapping("/valid")
    public ResponseEntity<String> isLoginValid(@Valid @RequestBody UserLoginRequest request) {
        userService.isLoginValid(request);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "Create")
    @Operation(summary = "Create user")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Tag(name = "Update")
    @Operation(summary = "Update user")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "Delete")
    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RESTAURANT_OWNER') || hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "Get")
    @Operation(summary = "Find user by name")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findUserByName(@RequestParam String name) {
        List<UserResponse> response = userService.findUserByName(name);
        return ResponseEntity.ok().body(response);
    }
}
