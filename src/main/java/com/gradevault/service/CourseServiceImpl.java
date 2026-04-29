package com.gradevault.service;

import com.gradevault.dto.CourseRequestDTO;
import com.gradevault.dto.CourseResponseDTO;
import com.gradevault.entity.Course;
import com.gradevault.exception.DuplicateResourceException;
import com.gradevault.exception.ResourceNotFoundException;
import com.gradevault.mapper.CourseMapper;
import com.gradevault.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    
    @Override
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.toResponseDTOList(courses);
    }
    
    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return courseMapper.toResponseDTO(course);
    }
    
    @Override
    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO request) {
        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new DuplicateResourceException("Course with code " + request.getCourseCode() + " already exists");
        }
        
        Course course = courseMapper.toEntity(request);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponseDTO(savedCourse);
    }
    
    @Override
    @Transactional
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO request) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        
        // Check courseCode uniqueness (excluding current course)
        if (!existingCourse.getCourseCode().equals(request.getCourseCode()) && 
            courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new DuplicateResourceException("Course with code " + request.getCourseCode() + " already exists");
        }
        
        existingCourse.setName(request.getName());
        existingCourse.setCourseCode(request.getCourseCode());
        existingCourse.setCredits(request.getCredits());
        
        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.toResponseDTO(updatedCourse);
    }
    
    @Override
    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}
