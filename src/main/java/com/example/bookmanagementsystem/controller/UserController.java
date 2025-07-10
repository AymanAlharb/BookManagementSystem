package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.exception.ApiResponse;
import com.example.bookmanagementsystem.model.User;
import com.example.bookmanagementsystem.model.dto.LoginRequest;
import com.example.bookmanagementsystem.model.dto.CreatingUserRequest;
import com.example.bookmanagementsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @Operation(
            summary = "Returns users",
            description = "Returns a list of users based on the offset and the page size, sort the users based on the field." +
                    " Only admins can access this endpoint.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/get-all-users/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<User>> getAllUsers(@Parameter(description = "offset, pageSize and field are required", required = true)
                                                      @PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(offset, pageSize, field));
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with a unique username and email. " +
                    "The password will be securely stored using encryption. " +
                    "A role must be provided during registration. " +
                    "If the email or username already exists in the system, an error will be returned.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "User details required for account creation"
            )
    )
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid CreatingUserRequest creatingUserRequest) throws IOException {
        userService.signup(creatingUserRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User signed up successfully."));
    }

    @Operation(
            summary = "Returns a JWT token to a signed user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Login information required for JWT creation"
            )
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginInfo) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(userService.verify(loginInfo)));
    }

}
