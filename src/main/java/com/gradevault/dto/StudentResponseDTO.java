package com.gradevault.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class StudentResponseDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
}
