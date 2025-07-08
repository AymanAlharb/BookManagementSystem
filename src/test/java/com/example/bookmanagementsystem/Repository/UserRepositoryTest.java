package com.example.bookmanagementsystem.Repository;

import com.example.bookmanagementsystem.Model.Role;
import com.example.bookmanagementsystem.Model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void init(){
        Role role = Role.builder().role("AUTHOR").build();
        user = User.builder()
                .username("ayman")
                .email("ayman@gmail.com")
                .password("12345678aA")
                .role(role)
                .build();
        role.setUser(user);
        userRepository.save(user);
    }

    @Test
    public void UserRepository_findUserById_returnUser(){
       User userById = userRepository.getUserById(user.getId());

       Assertions.assertThat(userById).isNotNull();
    }
    @Test
    public void UserRepository_findUserByUsername_ReturnsUser(){
        User userByUsername = userRepository.findUserByUsername(user.getUsername());

        Assertions.assertThat(userByUsername).isNotNull();
        Assertions.assertThat(userByUsername.getUsername()).isEqualTo("ayman");
    }

    @Test
    public void UserRepository_findUserByEmail_returnUser(){
        User userByEmail = userRepository.findUserByEmail(user.getEmail());

        Assertions.assertThat(userByEmail).isNotNull();
        Assertions.assertThat(userByEmail.getEmail()).isEqualTo("ayman@gmail.com");
    }

}
