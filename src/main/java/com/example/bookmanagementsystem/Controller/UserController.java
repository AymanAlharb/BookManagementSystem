package com.example.bookmanagementsystem.Controller;

import com.example.bookmanagementsystem.Api.ApiResponse;
import com.example.bookmanagementsystem.DtoIn.LoginDto;
import com.example.bookmanagementsystem.DtoIn.UserDtoIn;
import com.example.bookmanagementsystem.Model.User;
import com.example.bookmanagementsystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid UserDtoIn userDtoIn) {
        userService.signup(userDtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User signed up successfully."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginInfo){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(userService.verify(loginInfo)));
    }

}
