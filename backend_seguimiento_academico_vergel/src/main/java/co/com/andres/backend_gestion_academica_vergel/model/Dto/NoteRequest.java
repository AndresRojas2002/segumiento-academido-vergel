package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de solicitud para crear o actualizar una nota académica.
 * 
 * El valor de la nota debe estar entre 0.0 y 5.0 inclusive.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param enrollmentId ID de la matrícula a la que pertenece la nota
 * @param subjectId    ID de la materia a la que corresponde la nota
 * @param value        Valor numérico de la nota (entre 0.0 y 5.0)
 * @param period       Periodo académico de la nota (por ejemplo "2026-1")
 */
public record NoteRequest(

        @NotNull(message = "la matricula no puede ser nula")
        @JsonAlias({"matricula", "id_matricula"})
        Long enrollmentId,

        @NotNull(message = "la materia no puede ser nula")
        @JsonAlias({"materia", "id_materia"})
        Long subjectId,

        @NotNull(message = "el valor de la nota no puede ser nulo")
        @DecimalMin(value = "0.0", message = "la nota no puede ser menor a 0")
        @DecimalMax(value = "5.0", message = "la nota no puede ser mayor a 5")
        @JsonAlias({"valor", "nota", "calificacion"})
        Double value,

        @JsonAlias({"periodo"})
        String period) {
}