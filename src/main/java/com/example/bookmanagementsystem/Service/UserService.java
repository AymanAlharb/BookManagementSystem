package com.example.bookmanagementsystem.Service;

import com.example.bookmanagementsystem.Api.ApiException;
import com.example.bookmanagementsystem.DtoIn.LoginDto;
import com.example.bookmanagementsystem.DtoIn.UserDtoIn;
import com.example.bookmanagementsystem.Model.Role;
import com.example.bookmanagementsystem.Model.User;
import com.example.bookmanagementsystem.Repository.RoleRepository;
import com.example.bookmanagementsystem.Repository.UserRepository;
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


    public void signup(UserDtoIn userDtoIn) throws IOException {

        // Check if the email unique
        if (userRepository.findUserByEmail(userDtoIn.getEmail()) != null) {
            logger.warn("User with the username '{}' tried to sign up with a used email: '{}'",
                    userDtoIn.getUsername(), userDtoIn.getEmail());
            throw new ApiException("The email is used");
        }

        // Check if the username unique
        if (userRepository.findUserByUsername(userDtoIn.getUsername()) != null) {
            logger.warn("User with the email '{}' tried to sign up with a used username: '{}'",
                    userDtoIn.getEmail(), userDtoIn.getUsername());
            throw new ApiException("The username is used");
        }

        User user = new User(null, userDtoIn.getUsername(), userDtoIn.getEmail(),
                new BCryptPasswordEncoder().encode(userDtoIn.getPassword()), null, null);
        Role role = new Role(null, userDtoIn.getRole(), null);
        user.setRole(role);
        role.setUser(user);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), user.getUsername());
        logger.info("New user with the username '{}' and the role '{}' signed up successfully.",
                user.getUsername(), user.getRole().getRole());

    }

    public String verify(LoginDto loginInfo){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                        (loginInfo.getUsername(), loginInfo.getPassword()));

        if(!authentication.isAuthenticated()){
            throw new ApiException("Password or username incorrect.");
        }

        return jwtService.generateToken(loginInfo.getUsername());
    }
}
