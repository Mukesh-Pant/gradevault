package com.gradevault.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CourseResponseDTO {
    private Long id;
    private String name;
    private String courseCode;
    private Integer credits;
    private LocalDateTime createdAt;
}
