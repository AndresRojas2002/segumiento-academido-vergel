package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de solicitud para crear o actualizar un grado.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param nameGrade Nombre del grado (no puede ser nulo ni vacío)
 */
public record GradeRequest(

    @NotBlank(message = "el nombre del grado no puede ser nulo")
    @JsonAlias({"nombre_grado", "grado"})
    String nameGrade) {

}