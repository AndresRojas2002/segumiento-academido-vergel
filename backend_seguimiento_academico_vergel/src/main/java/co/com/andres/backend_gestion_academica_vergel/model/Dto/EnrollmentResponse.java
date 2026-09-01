package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import java.time.LocalDate;

import co.com.andres.backend_gestion_academica_vergel.model.shared.EnrollmentsState;

/**
 * DTO de respuesta con la información de una matrícula.
 * 
 * Contiene los datos relevantes de la matrícula para ser
 * enviados al cliente, incluyendo información del estudiante,
 * el grado y el estado actual.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param id                Identificador único de la matrícula
 * @param nameStudents      Nombre del estudiante matriculado
 * @param lastNameStudent   Apellido del estudiante matriculado
 * @param nameGrade         Nombre del grado al que pertenece la matrícula
 * @param enrollmentDate    Fecha en la que se realizó la matrícula
 * @param enrollmentsState  Estado actual de la matrícula
 */
public record EnrollmentResponse(
        Long id,
        String nameStudents,
        String lastNameStudent,
        String nameGrade,
        LocalDate enrollmentDate,
        EnrollmentsState enrollmentsState) {

}
