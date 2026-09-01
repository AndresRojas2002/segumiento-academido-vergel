package co.com.andres.backend_gestion_academica_vergel.service;

import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.EnrollmentRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.EnrollmentResponse;

public interface EnrollmentService {

    EnrollmentResponse createEnrollments(EnrollmentRequest enrollmentRequest);

    List<EnrollmentResponse> getAllEnrollments();

    EnrollmentResponse getByIdEnrollments(Long id);

    void deleteEnrollments(Long id);

    void retiredEnrollments(Long id);
}
