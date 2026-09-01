package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

/**
 * DTO de solicitud para crear o actualizar una matrícula.
 * 
 * Si no se proporciona una fecha de matrícula, se asigna automáticamente
 * la fecha actual del sistema.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param students       ID del estudiante a matricular
 * @param idGrado        ID del grado al que se matricula el estudiante
 * @param enrollmentDate Fecha de la matrícula (por defecto: fecha actual)
 */
public record EnrollmentRequest(

    @NotNull(message = "el id del estudiante no puede ser nulo")
    @JsonAlias({"estudiante"})
    Long students,

    @NotNull(message = "el grado no puede ser nulo")
    @JsonAlias({"grado", "id_grado"})
    Long idGrado,

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAlias({"fecha", "fecha_registro"})
    LocalDate enrollmentDate) {

    public EnrollmentRequest {
        if (enrollmentDate == null) {
            enrollmentDate = LocalDate.now();
        }
    }
}