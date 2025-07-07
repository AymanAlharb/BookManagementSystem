package com.example.bookmanagementsystem.Controller;

import com.example.bookmanagementsystem.Api.ApiResponse;
import com.example.bookmanagementsystem.DtoIn.LoginDto;
import com.example.bookmanagementsystem.DtoIn.UserDtoIn;
import com.example.bookmanagementsystem.Service.EmailService;
import com.example.bookmanagementsystem.Service.UserService;
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
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid UserDtoIn userDtoIn) throws IOException {
        userService.signup(userDtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User signed up successfully."));
    }

    @Operation(
            description = "Returns a JWT token."
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginInfo){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(userService.verify(loginInfo)));
    }

//    @PostMapping("/send")
//    public ResponseEntity<ApiResponse> sendEmail() throws IOException {
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(emailService.sendEmail()));
//    }

}
