package com.example.bookmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The role can not be empty.")
    @Pattern(regexp = "ADMIN|USER|AUTHOR")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) not null")
    private RoleEnum role;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
