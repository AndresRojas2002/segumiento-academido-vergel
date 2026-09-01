package co.com.andres.backend_gestion_academica_vergel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.GradeRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.GradeResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Grade;

/**
 * Mapper para la conversión entre la entidad {@link Grade} y sus DTOs.
 *
 * Utiliza MapStruct para generar automáticamente la implementación
 * en tiempo de compilación.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Mapper(componentModel = "spring")
public interface GradeMapper {

    /**
     * Convierte un {@link GradeRequest} a la entidad {@link Grade}.
     * El {@code id}, {@code enrollments} y {@code subjects} se gestionan fuera del mapper.
     *
     * @param request DTO con los datos del grado
     * @return entidad {@link Grade} mapeada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    Grade toEntity(GradeRequest request);

    /**
     * Convierte una entidad {@link Grade} a {@link GradeResponse}.
     *
     * @param entity entidad del grado
     * @return DTO de respuesta con la información del grado
     */
    GradeResponse toResponse(Grade entity);

    /**
     * Actualiza una entidad {@link Grade} existente con los datos del request.
     * El {@code id}, {@code enrollments} y {@code subjects} se conservan sin modificación.
     *
     * @param request DTO con los nuevos datos
     * @param entity  entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    void updateEntityFromRequest(GradeRequest request, @MappingTarget Grade entity);
}
