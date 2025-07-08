package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.model.dto.LoginRequest;
import com.example.bookmanagementsystem.model.dto.CreatingUserRequest;
import com.example.bookmanagementsystem.service.JWTService;
import com.example.bookmanagementsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTService jwtService;

    private CreatingUserRequest user;

    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

    private LoginRequest loginRequest;
    @BeforeEach
    void init() {
        user = CreatingUserRequest.builder()
                .username("ayman")
                .email("ayman@gmail.com")
                .password("12345678aA")
                .role("USER")
                .build();

        loginRequest = LoginRequest.builder()
                .password("12345678aA")
                .username("ayman")
                .build();
    }

    @Test
    public void UserController_Signup_ReturnSuccess() throws Exception {
        doNothing().when(userService).signup(any(CreatingUserRequest.class));

        ResultActions response = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User signed up successfully."));
    }

    @Test
    public void UserController_Login_ReturnSuccess() throws Exception {
        when(userService.verify(any(LoginRequest.class))).thenReturn(token);

        ResultActions response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(token));
    }
}
