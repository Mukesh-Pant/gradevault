package com.gradevault.service;

import com.gradevault.dto.EnrollmentRequestDTO;
import com.gradevault.dto.EnrollmentResponseDTO;
import com.gradevault.dto.EnrollmentUpdateDTO;
import com.gradevault.entity.Course;
import com.gradevault.entity.Enrollment;
import com.gradevault.entity.Student;
import com.gradevault.exception.DuplicateResourceException;
import com.gradevault.exception.ResourceNotFoundException;
import com.gradevault.mapper.EnrollmentMapper;
import com.gradevault.repository.CourseRepository;
import com.gradevault.repository.EnrollmentRepository;
import com.gradevault.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;
    
    @Override
    public List<EnrollmentResponseDTO> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollmentMapper.toResponseDTOList(enrollments);
    }
    
    @Override
    @Transactional
    public EnrollmentResponseDTO createEnrollment(EnrollmentRequestDTO request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.getStudentId()));
        
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + request.getCourseId()));
        
        if (enrollmentRepository.existsByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())) {
            throw new DuplicateResourceException("Student is already enrolled in this course");
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(request.getGrade());
        
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponseDTO(savedEnrollment);
    }
    
    @Override
    @Transactional
    public EnrollmentResponseDTO updateEnrollment(Long id, EnrollmentUpdateDTO request) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        
        enrollment.setGrade(request.getGrade());
        
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponseDTO(updatedEnrollment);
    }
    
    @Override
    @Transactional
    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Enrollment not found with id: " + id);
        }
        enrollmentRepository.deleteById(id);
    }
}
