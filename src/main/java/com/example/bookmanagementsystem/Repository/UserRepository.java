package com.example.bookmanagementsystem.Repository;

import com.example.bookmanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long userId);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
