package com.gradevault.service;

import com.gradevault.dto.CourseResponseDTO;
import com.gradevault.dto.StudentRequestDTO;
import com.gradevault.dto.StudentResponseDTO;
import com.gradevault.dto.StudentStatsDTO;

import java.util.List;

public interface StudentService {
    
    List<StudentResponseDTO> getAllStudents();
    
    StudentResponseDTO getStudentById(Long id);
    
    StudentResponseDTO createStudent(StudentRequestDTO request);
    
    StudentResponseDTO updateStudent(Long id, StudentRequestDTO request);
    
    void deleteStudent(Long id);
    
    StudentStatsDTO getStudentStats(Long id);
    
    List<CourseResponseDTO> getStudentCourses(Long id);
}
