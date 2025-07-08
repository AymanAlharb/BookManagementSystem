package com.example.bookmanagementsystem.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CreatingBookRequest {
    @NotEmpty(message = "The title can not be empty.")
    @Column(columnDefinition = "varchar(60) not null")
    private String title;

    @NotEmpty(message = "The author name can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String authorName;

    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(255) not null")
    private String description;
}
