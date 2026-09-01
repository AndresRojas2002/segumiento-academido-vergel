package co.com.andres.backend_gestion_academica_vergel.model.Dto;

/**
 * DTO de respuesta con la información de una materia.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param id             Identificador único de la materia
 * @param nameSubject    Nombre de la materia
 * @param description    Descripción del contenido de la materia
 * @param nameProfessors Nombre del profesor responsable de la materia
 * @param nameGrade      Nombre del grado al que pertenece la materia
 */
public record SubjectResponse(
        Long id,
        String nameSubject,
        String description,
        String nameProfessors,
        String nameGrade) {

}