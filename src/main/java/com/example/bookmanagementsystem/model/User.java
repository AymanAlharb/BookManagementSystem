package com.example.bookmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(20) unique not null")
    private String username;

    @Email
    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(40) unique not null")
    private String email;

    @NotEmpty(message = "The description can not be empty.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$")
    @Column(columnDefinition = "varchar(60) not null")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Role role;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private Set<Book> bookSet;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.getRole().name()));
    }
}
