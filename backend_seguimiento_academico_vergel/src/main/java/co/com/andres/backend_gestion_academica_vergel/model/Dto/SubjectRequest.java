package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de solicitud para crear o actualizar una materia.
 * 
 * Si no se proporciona una descripción, se asigna
 * "no hay descripcion disponible" como valor por defecto.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param nameSubject Nombre de la materia
 * @param description Descripción del contenido de la materia (opcional)
 * @param professors  ID del profesor responsable de la materia
 * @param gradeId     ID del grado al que pertenece la materia
 */
public record SubjectRequest(

        @NotBlank(message = "el curso no puede ser nulo")
        @JsonAlias({"nombre_curso", "curso"})
        String nameSubject,

        @JsonAlias({"descripcion"})
        String description,

        @NotNull(message = "el curso debe tener un profesor asignado")
        @JsonAlias({"profesor", "maestro"})
        Long professorId,

        @NotNull(message = "el grado no puede ser nulo")
        @JsonAlias({"grado", "id_grado"})
        Long gradeId) {

    public SubjectRequest {
        if (description == null) {
            description = "no hay descripcion disponible";
        }
    }
}