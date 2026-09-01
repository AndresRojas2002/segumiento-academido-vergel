package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import java.time.LocalDate;

import co.com.andres.backend_gestion_academica_vergel.model.shared.AttendanceState;

/**
 * DTO de respuesta con la información de un registro de asistencia.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 *
 * @param id              Identificador único del registro
 * @param enrollmentId    Identificador de la matrícula asociada
 * @param studentName     Nombre del estudiante
 * @param studentLastName Apellido del estudiante
 * @param subjectName     Nombre de la materia
 * @param date            Fecha de la sesión registrada
 * @param state           Estado de la asistencia
 * @param period          Periodo académico
 */
public record AttendanceResponse(
        Long id,
        Long enrollmentId,
        String studentName,
        String studentLastName,
        String subjectName,
        LocalDate date,
        AttendanceState state,
        String period) {
}
