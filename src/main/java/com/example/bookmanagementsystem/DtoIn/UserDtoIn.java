package com.example.bookmanagementsystem.DtoIn;

import com.example.bookmanagementsystem.Model.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoIn {
    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String username;

    @Email
    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(40) not null")
    private String email;

    @NotEmpty(message = "The description can not be empty.")
    //@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$")
    @Column(columnDefinition = "varchar(60) not null")
    private String password;

    @Pattern(regexp = "AUTHOR|ADMIN|USER")
    @NotEmpty(message = "The role can not be empty.")
    private String role;
}
