package com.example.bookmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The title can not be empty.")
    @Column(columnDefinition = "varchar(60) not null")
    private String title;

    @NotEmpty(message = "The author name can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String authorName;

    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(255) not null")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User createdBy;
}
