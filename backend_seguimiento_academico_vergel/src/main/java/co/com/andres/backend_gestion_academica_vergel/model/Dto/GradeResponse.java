package co.com.andres.backend_gestion_academica_vergel.model.Dto;

/**
 * DTO de respuesta con la información de un grado.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param id        Identificador único del grado
 * @param nameGrade Nombre del grado
 */
public record GradeResponse(
    Long id,
    String nameGrade) {

}