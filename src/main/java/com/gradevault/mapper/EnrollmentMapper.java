package com.gradevault.mapper;

import com.gradevault.dto.EnrollmentResponseDTO;
import com.gradevault.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.name", target = "studentName")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    EnrollmentResponseDTO toResponseDTO(Enrollment enrollment);
    
    List<EnrollmentResponseDTO> toResponseDTOList(List<Enrollment> enrollments);
}
