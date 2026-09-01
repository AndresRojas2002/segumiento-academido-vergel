package co.com.andres.backend_gestion_academica_vergel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.SubjectRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.SubjectResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Subject;

/**
 * Mapper para la conversión entre la entidad {@link Subject} y sus DTOs.
 * 
 * Utiliza MapStruct para generar automáticamente la implementación
 * en tiempo de compilación.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Mapper(componentModel = "spring")
public interface SubjectMapper {

    /**
     * Convierte un {@link SubjectRequest} a la entidad {@link Subject}.
     * Los campos {@code professors}, {@code grade} e {@code id} se resuelven
     * fuera del mapper.
     *
     * @param subjectDto DTO con los datos de la materia
     * @return entidad {@link Subject} mapeada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "professors", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "notes", ignore = true)
    Subject toEntity(SubjectRequest subjectDto);

    /**
     * Convierte una entidad {@link Subject} a {@link SubjectResponse}.
     *
     * @param subjectEntity entidad de la materia
     * @return DTO de respuesta con la información de la materia
     */
    @Mapping(target = "nameProfessors", source = "professors.name")
    @Mapping(target = "nameGrade", source = "grade.nameGrade")
    SubjectResponse toResponse(Subject subjectEntity);

    /**
     * Actualiza una entidad {@link Subject} existente con los datos del request.
     * Los campos {@code professors}, {@code grade} e {@code id} se conservan
     * sin modificación.
     *
     * @param request DTO con los nuevos datos
     * @param subject entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "professors", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "notes", ignore = true)
    void updateEntityFromRequest(SubjectRequest request, @MappingTarget Subject subject);
}

