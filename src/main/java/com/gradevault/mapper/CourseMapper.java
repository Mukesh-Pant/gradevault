package com.gradevault.mapper;

import com.gradevault.dto.CourseRequestDTO;
import com.gradevault.dto.CourseResponseDTO;
import com.gradevault.entity.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    
    CourseResponseDTO toResponseDTO(Course course);
    
    Course toEntity(CourseRequestDTO requestDTO);
    
    List<CourseResponseDTO> toResponseDTOList(List<Course> courses);
}
