package com.filipearruda.tech_challenge_restaurant_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.UserService;
import com.filipearruda.tech_challenge_restaurant_manager.domain.user.request.UserLoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final ObjectMapper mapper;

    UserControllerTest() {
        this.mapper = new ObjectMapper();
    }

    @Test
    void shouldLoginValid() throws Exception {
        //Arrange
        UserLoginRequest request = new UserLoginRequest("admin", "admin");

        //Act
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/user/valid")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void shouldLoginNotValid() throws Exception {
        //Arrange
        UserLoginRequest request = new UserLoginRequest("", "");
        Mockito.doThrow(new BadCredentialsException("Invalid credentials"))
                .when(userService)
                .isLoginValid(Mockito.any(UserLoginRequest.class));

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/v1/user/valid")
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();

        //Assert
        Assertions.assertEquals(401, response.getStatus());
    }
}