package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.exception.ApiException;
import com.example.bookmanagementsystem.model.RoleEnum;
import com.example.bookmanagementsystem.model.dto.LoginRequest;
import com.example.bookmanagementsystem.model.dto.CreatingUserRequest;
import com.example.bookmanagementsystem.model.Role;
import com.example.bookmanagementsystem.model.User;
import com.example.bookmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final EmailService emailService;
    private static final Logger logger = LogManager.getLogger(UserService.class);


    public void signup(CreatingUserRequest creatingUserRequest) throws IOException {

        // Check if the email unique
        if (userRepository.findUserByEmail(creatingUserRequest.getEmail()) != null) {
            logger.warn("User with the username '{}' tried to sign up with a used email: '{}'",
                    creatingUserRequest.getUsername(), creatingUserRequest.getEmail());
            throw new ApiException("The email is used");
        }

        // Check if the username unique
        if (userRepository.findUserByUsername(creatingUserRequest.getUsername()) != null) {
            logger.warn("User with the email '{}' tried to sign up with a used username: '{}'",
                    creatingUserRequest.getEmail(), creatingUserRequest.getUsername());
            throw new ApiException("The username is used");
        }

        User user = new User(null, creatingUserRequest.getUsername(), creatingUserRequest.getEmail(),
                new BCryptPasswordEncoder().encode(creatingUserRequest.getPassword()), null, null);

        Role role = new Role();
        setUserRole(creatingUserRequest.getRole(), role);
        user.setRole(role);
        role.setUser(user);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), user.getUsername());
        logger.info("New user with the username '{}' and the role '{}' signed up successfully.",
                user.getUsername(), user.getRole().getRole());

    }

    public String verify(LoginRequest loginInfo){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                        (loginInfo.getUsername(), loginInfo.getPassword()));

        if(!authentication.isAuthenticated()){
            throw new ApiException("Password or username incorrect.");
        }

        return jwtService.generateToken(loginInfo.getUsername());
    }

    public void setUserRole(String roleType, Role role){
        switch (roleType){
            case "ADMIN":
                role.setRole(RoleEnum.ADMIN);
                break;
            case "USER":
                role.setRole(RoleEnum.USER);
                break;
            default:
                role.setRole(RoleEnum.AUTHOR);
        }
    }
}
