package com.example.bookmanagementsystem.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String username;
    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(60) not null")
    private String password;

}
