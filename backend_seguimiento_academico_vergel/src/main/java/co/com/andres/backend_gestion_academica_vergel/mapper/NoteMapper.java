package co.com.andres.backend_gestion_academica_vergel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.NoteRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.NoteResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Note;

/**
 * Mapper para la conversión entre la entidad {@link Note} y sus DTOs.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Mapper(componentModel = "spring")
public interface NoteMapper {

    /**
     * Convierte un {@link NoteRequest} a la entidad {@link Note}.
     * El {@code id}, {@code enrollments} y {@code subject} se resuelven
     * fuera del mapper, en el service, a partir de {@code enrollmentId}
     * y {@code subjectId}.
     *
     * @param request DTO con los datos de la nota
     * @return entidad {@link Note} mapeada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "subject", ignore = true)
    Note toEntity(NoteRequest request);

    /**
     * Convierte una entidad {@link Note} a {@link NoteResponse}.
     * El campo {@code professorName} se calcula de forma segura mediante
     * {@link #mapProfessorName(Note)}, devolviendo {@code null} si la materia
     * no tiene un profesor asignado.
     *
     * @param entity entidad de la nota
     * @return DTO de respuesta con la información de la nota
     */
    @Mapping(target = "studentName", source = "enrollments.students.name")
    @Mapping(target = "studentLastName", source = "enrollments.students.lastName")
    @Mapping(target = "subjectName", source = "subject.nameSubject")
    @Mapping(target = "professorName", expression = "java(mapProfessorName(entity))")
    NoteResponse toResponse(Note entity);

    /**
     * Actualiza una entidad {@link Note} existente con los datos del request.
     * El {@code id}, {@code enrollments} y {@code subject} se resuelven
     * fuera del mapper.
     *
     * @param request DTO con los nuevos datos
     * @param entity  entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "subject", ignore = true)
    void updateEntityFromRequest(NoteRequest request, @MappingTarget Note entity);

    /**
     * Construye el nombre completo del profesor responsable de la materia.
     *
     * @param entity entidad de la nota
     * @return nombre y apellido del profesor, o {@code null} si la materia
     *         no tiene un profesor asignado
     */
    default String mapProfessorName(Note entity) {
        if (entity.getSubject() == null || entity.getSubject().getProfessors() == null) {
            return null;
        }
        var professor = entity.getSubject().getProfessors();
        return professor.getName() + " " + professor.getLastName();
    }
}