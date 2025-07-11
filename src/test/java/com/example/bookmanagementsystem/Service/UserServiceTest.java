package com.example.bookmanagementsystem.Service;

import com.example.bookmanagementsystem.Api.ApiException;
import com.example.bookmanagementsystem.DtoIn.UserDtoIn;
import com.example.bookmanagementsystem.Model.Role;
import com.example.bookmanagementsystem.Model.User;
import com.example.bookmanagementsystem.Repository.UserRepository;
import io.jsonwebtoken.JweHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDtoIn user;

    @BeforeEach
    void init(){
        user = UserDtoIn.builder()
                .username("ayman")
                .email("ayman@gmail.com")
                .password("12345678aA")
                .build();
    }

    @Test
    public void UserService_Signup_ReturnSuccess(){
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(null);

        userService.signup(user);

        verify(userRepository).save(any(User.class));

    }

    @Test
    public void UserService_Signup_ReturnUsedEmail() {
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(new User());

        ApiException response = assertThrows(ApiException.class, () -> userService.signup(user));

        assertEquals("The email is used", response.getMessage());

        verify(userRepository, never()).save(any());
    }

    @Test
    public void UserService_Signup_ReturnUsedUserName() {
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(new User());

        ApiException response = assertThrows(ApiException.class, () -> userService.signup(user));

        assertEquals("The username is used", response.getMessage());

        verify(userRepository, never()).save(any());
    }

}
