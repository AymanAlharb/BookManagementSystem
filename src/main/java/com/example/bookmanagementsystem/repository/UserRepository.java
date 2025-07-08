package com.example.bookmanagementsystem.repository;

import com.example.bookmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long userId);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
