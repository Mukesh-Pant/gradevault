package com.gradevault.controller;

import com.gradevault.dto.ApiResponse;
import com.gradevault.dto.CourseResponseDTO;
import com.gradevault.dto.StudentRequestDTO;
import com.gradevault.dto.StudentResponseDTO;
import com.gradevault.dto.StudentStatsDTO;
import com.gradevault.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success(students, "Students retrieved successfully"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudentById(@PathVariable Long id) {
        StudentResponseDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success(student, "Student retrieved successfully"));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDTO>> createStudent(@Valid @RequestBody StudentRequestDTO request) {
        StudentResponseDTO student = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(student, "Student created successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudent(
            @PathVariable Long id, 
            @Valid @RequestBody StudentRequestDTO request) {
        StudentResponseDTO student = studentService.updateStudent(id, request);
        return ResponseEntity.ok(ApiResponse.success(student, "Student updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Student deleted successfully"));
    }
    
    @GetMapping("/{id}/stats")
    public ResponseEntity<ApiResponse<StudentStatsDTO>> getStudentStats(@PathVariable Long id) {
        StudentStatsDTO stats = studentService.getStudentStats(id);
        return ResponseEntity.ok(ApiResponse.success(stats, "Student statistics retrieved successfully"));
    }
    
    @GetMapping("/{id}/courses")
    public ResponseEntity<ApiResponse<List<CourseResponseDTO>>> getStudentCourses(@PathVariable Long id) {
        List<CourseResponseDTO> courses = studentService.getStudentCourses(id);
        return ResponseEntity.ok(ApiResponse.success(courses, "Student courses retrieved successfully"));
    }
}
