package com.example.bookmanagementsystem.Service;

import com.example.bookmanagementsystem.Model.Role;
import com.example.bookmanagementsystem.Model.User;
import com.example.bookmanagementsystem.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(AdminService.class);

    @PostConstruct
    private void postConstruct() {
        User admin = new User(null, "admin", "admin@gmail.com", new BCryptPasswordEncoder().encode("123"), null, null);
        Role role = new Role(null, "ADMIN", null);
        admin.setRole(role);
        role.setUser(admin);
        userRepository.save(admin);
        logger.info("Admin add at start up successfully.");

    }
}
