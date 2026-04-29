package com.gradevault.service;

import com.gradevault.dto.EnrollmentRequestDTO;
import com.gradevault.dto.EnrollmentResponseDTO;
import com.gradevault.dto.EnrollmentUpdateDTO;

import java.util.List;

public interface EnrollmentService {
    
    List<EnrollmentResponseDTO> getAllEnrollments();
    
    EnrollmentResponseDTO createEnrollment(EnrollmentRequestDTO request);
    
    EnrollmentResponseDTO updateEnrollment(Long id, EnrollmentUpdateDTO request);
    
    void deleteEnrollment(Long id);
}
