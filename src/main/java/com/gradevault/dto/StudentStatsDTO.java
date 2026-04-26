package com.gradevault.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentStatsDTO {
    private Long studentId;
    private String studentName;
    private Double averageGrade;
    private Integer totalCourses;
    private Double highestGrade;
}
