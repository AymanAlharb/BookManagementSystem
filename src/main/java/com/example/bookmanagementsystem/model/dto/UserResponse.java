package com.example.bookmanagementsystem.model.dto;

import com.example.bookmanagementsystem.model.Book;
import com.example.bookmanagementsystem.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserResponse {

    private String username;
    private String email;


    private Role role;

    private Set<Book> bookSet;
}
