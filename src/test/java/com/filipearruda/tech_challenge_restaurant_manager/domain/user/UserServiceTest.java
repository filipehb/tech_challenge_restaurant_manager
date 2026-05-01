package com.filipearruda.tech_challenge_restaurant_manager.domain.user;

import com.filipearruda.tech_challenge_restaurant_manager.config.exception.ValidationException;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.ChangeUserPasswordRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.CreateUserRequest;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.response.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    User user;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldChageUserPassword() {
        //Arrange
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String email = "email";
        ChangeUserPasswordRequest request = new ChangeUserPasswordRequest(email, oldPassword, newPassword);
        BDDMockito.given(userRepository.findByEmail(email)).willReturn(Optional.ofNullable(user));
        BDDMockito.given(user.getPassword()).willReturn(oldPassword);
        BDDMockito.given(passwordEncoder.matches(Mockito.any(), Mockito.any())).willReturn(true);

        //Act
        userService.changeUserPassword(request);

        //Assert
        Assertions.assertEquals(oldPassword, user.getPassword());
    }

    @Test
    void shouldNotChageUserPassword() {
        //Arrange
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String email = "email";
        ChangeUserPasswordRequest request = new ChangeUserPasswordRequest(email, oldPassword, newPassword);
        BDDMockito.given(userRepository.findByEmail(email)).willReturn(Optional.ofNullable(user));
        BDDMockito.given(user.getPassword()).willReturn(oldPassword);
        BDDMockito.given(passwordEncoder.matches(Mockito.any(), Mockito.any())).willReturn(false);

        //Act + Assert
        Assertions.assertThrows(ValidationException.class, () -> userService.changeUserPassword(request));
    }

    @Test
    void shouldCreateUser() {
        //Arrange
        CreateUserRequest request = new CreateUserRequest(
                "name",
                "email",
                "login",
                "password",
                "address",
                Profile.CLIENT);
        BDDMockito.given(userRepository.save(Mockito.any(User.class))).willReturn(user);

        //Act
        UserResponse response = userService.createUser(request);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.email(), user.getEmail());
    }

    @Test
    void shouldNotCreateUser() {
        //Arrange
        CreateUserRequest request = new CreateUserRequest(
                "name",
                "email",
                "login",
                "password",
                "address",
                Profile.CLIENT
        );
        BDDMockito.given(userRepository.save(Mockito.any(User.class))).willThrow(DataIntegrityViolationException.class);

        //Act + Assert
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(request));
    }
}