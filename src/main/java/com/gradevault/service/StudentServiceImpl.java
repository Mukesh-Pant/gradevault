package com.gradevault.service;

import com.gradevault.dto.CourseResponseDTO;
import com.gradevault.dto.StudentRequestDTO;
import com.gradevault.dto.StudentResponseDTO;
import com.gradevault.dto.StudentStatsDTO;
import com.gradevault.entity.Enrollment;
import com.gradevault.entity.Student;
import com.gradevault.exception.DuplicateResourceException;
import com.gradevault.exception.ResourceNotFoundException;
import com.gradevault.mapper.CourseMapper;
import com.gradevault.mapper.StudentMapper;
import com.gradevault.repository.EnrollmentRepository;
import com.gradevault.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    
    @Override
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toResponseDTOList(students);
    }
    
    @Override
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return studentMapper.toResponseDTO(student);
    }
    
    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Student with email " + request.getEmail() + " already exists");
        }
        
        Student student = studentMapper.toEntity(request);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseDTO(savedStudent);
    }
    
    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO request) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        
        // Check email uniqueness (excluding current student)
        if (!existingStudent.getEmail().equals(request.getEmail()) && 
            studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Student with email " + request.getEmail() + " already exists");
        }
        
        existingStudent.setName(request.getName());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setDateOfBirth(request.getDateOfBirth());
        
        Student updatedStudent = studentRepository.save(existingStudent);
        return studentMapper.toResponseDTO(updatedStudent);
    }
    
    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
    
    @Override
    public StudentStatsDTO getStudentStats(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(id);
        
        if (enrollments.isEmpty()) {
            return StudentStatsDTO.builder()
                    .studentId(student.getId())
                    .studentName(student.getName())
                    .averageGrade(0.0)
                    .totalCourses(0)
                    .highestGrade(0.0)
                    .build();
        }
        
        double averageGrade = enrollments.stream()
                .mapToDouble(Enrollment::getGrade)
                .average()
                .orElse(0.0);
        
        int totalCourses = enrollments.size();
        
        double highestGrade = enrollments.stream()
                .mapToDouble(Enrollment::getGrade)
                .max()
                .orElse(0.0);
        
        return StudentStatsDTO.builder()
                .studentId(student.getId())
                .studentName(student.getName())
                .averageGrade(averageGrade)
                .totalCourses(totalCourses)
                .highestGrade(highestGrade)
                .build();
    }
    
    @Override
    public List<CourseResponseDTO> getStudentCourses(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(id);
        return enrollments.stream()
                .map(enrollment -> courseMapper.toResponseDTO(enrollment.getCourse()))
                .collect(Collectors.toList());
    }
}
