package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.exception.ApiResponse;
import com.example.bookmanagementsystem.model.dto.LoginRequest;
import com.example.bookmanagementsystem.model.dto.CreatingUserRequest;
import com.example.bookmanagementsystem.service.EmailService;
import com.example.bookmanagementsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid CreatingUserRequest creatingUserRequest) throws IOException {
        userService.signup(creatingUserRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User signed up successfully."));
    }

    @Operation(
            description = "Returns a JWT token."
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginInfo){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(userService.verify(loginInfo)));
    }

}
