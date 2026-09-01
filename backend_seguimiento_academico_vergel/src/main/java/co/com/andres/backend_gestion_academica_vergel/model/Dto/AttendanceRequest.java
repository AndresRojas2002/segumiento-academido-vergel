package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import co.com.andres.backend_gestion_academica_vergel.model.shared.AttendanceState;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de solicitud para registrar o actualizar una asistencia.
 *
 * Si no se envía la fecha, se asigna automáticamente la fecha actual.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 *
 * @param enrollmentId ID de la matrícula (estudiante) a la que pertenece
 * @param subjectId    ID de la materia en la que se toma asistencia
 * @param date         Fecha de la sesión (por defecto: fecha actual)
 * @param state        Estado de la asistencia (PRESENT, ABSENT, LATE, EXCUSED)
 * @param period       Periodo académico (por ejemplo "2026-1")
 */
public record AttendanceRequest(

        @NotNull(message = "la matricula no puede ser nula")
        @JsonAlias({"matricula", "id_matricula"})
        Long enrollmentId,

        @NotNull(message = "la materia no puede ser nula")
        @JsonAlias({"materia", "id_materia"})
        Long subjectId,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonAlias({"fecha"})
        LocalDate date,

        @NotNull(message = "el estado de la asistencia no puede ser nulo")
        @JsonAlias({"estado"})
        AttendanceState state,

        @JsonAlias({"periodo"})
        String period) {

    public AttendanceRequest {
        if (date == null) {
            date = LocalDate.now();
        }
    }
}
