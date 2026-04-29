package com.gradevault.controller;

import com.gradevault.dto.ApiResponse;
import com.gradevault.dto.EnrollmentRequestDTO;
import com.gradevault.dto.EnrollmentResponseDTO;
import com.gradevault.dto.EnrollmentUpdateDTO;
import com.gradevault.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    
    private final EnrollmentService enrollmentService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<EnrollmentResponseDTO>>> getAllEnrollments() {
        List<EnrollmentResponseDTO> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(ApiResponse.success(enrollments, "Enrollments retrieved successfully"));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> createEnrollment(@Valid @RequestBody EnrollmentRequestDTO request) {
        EnrollmentResponseDTO enrollment = enrollmentService.createEnrollment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(enrollment, "Enrollment created successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> updateEnrollment(
            @PathVariable Long id, 
            @Valid @RequestBody EnrollmentUpdateDTO request) {
        EnrollmentResponseDTO enrollment = enrollmentService.updateEnrollment(id, request);
        return ResponseEntity.ok(ApiResponse.success(enrollment, "Enrollment updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Enrollment deleted successfully"));
    }
}
