package com.gradevault.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequestDTO {
    
    @NotBlank(message = "Course name is required")
    private String name;
    
    @NotBlank(message = "Course code is required")
    private String courseCode;
    
    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 6, message = "Credits must be at most 6")
    private Integer credits;
}
