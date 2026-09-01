package co.com.andres.backend_gestion_academica_vergel.service;

import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.AttendanceRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AttendanceResponse;

/**
 * Contrato del servicio de gestión de asistencia.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface AttendanceService {

    AttendanceResponse create(AttendanceRequest request);

    List<AttendanceResponse> findAll();

    AttendanceResponse findById(Long id);

    List<AttendanceResponse> findByEnrollment(Long enrollmentId);

    AttendanceResponse update(Long id, AttendanceRequest request);

    void delete(Long id);
}
