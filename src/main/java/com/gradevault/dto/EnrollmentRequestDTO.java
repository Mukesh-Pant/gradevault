package com.gradevault.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequestDTO {
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    @NotNull(message = "Course ID is required")
    private Long courseId;
    
    @NotNull(message = "Grade is required")
    @DecimalMin(value = "0.0", message = "Grade must be at least 0.0")
    @DecimalMax(value = "100.0", message = "Grade must be at most 100.0")
    private Double grade;
}
