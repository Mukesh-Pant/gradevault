package com.gradevault.service;

import com.gradevault.dto.CourseRequestDTO;
import com.gradevault.dto.CourseResponseDTO;

import java.util.List;

public interface CourseService {
    
    List<CourseResponseDTO> getAllCourses();
    
    CourseResponseDTO getCourseById(Long id);
    
    CourseResponseDTO createCourse(CourseRequestDTO request);
    
    CourseResponseDTO updateCourse(Long id, CourseRequestDTO request);
    
    void deleteCourse(Long id);
}
