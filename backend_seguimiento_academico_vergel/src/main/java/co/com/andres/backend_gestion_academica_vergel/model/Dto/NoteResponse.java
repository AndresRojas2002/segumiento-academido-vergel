package co.com.andres.backend_gestion_academica_vergel.model.Dto;

/**
 * DTO de respuesta con la información de una nota académica.
 * 
 * Incluye datos del estudiante, la materia, el profesor responsable
 * y el valor numérico obtenido.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param id              Identificador único de la nota
 * @param studentName     Nombre del estudiante
 * @param studentLastName Apellido del estudiante
 * @param subjectName     Nombre de la materia
 * @param professorName   Nombre del profesor responsable de la materia
 * @param value           Valor numérico de la nota obtenida
 * @param period          Periodo académico de la nota
 */
public record NoteResponse(
        Long id,

        // datos de matrícula
        String studentName,

        String studentLastName,

        // datos de materia
        String subjectName,

        String professorName,

        // dato de nota
        Double value,

        String period) {

}