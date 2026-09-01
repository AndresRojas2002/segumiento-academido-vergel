package co.com.andres.backend_gestion_academica_vergel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.FollowupRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.FollowupResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Followup;

/**
 * Mapper para la conversión entre la entidad {@link Followup} y sus DTOs.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Mapper(componentModel = "spring")
public interface FollowupMapper {

    /**
     * Convierte un {@link FollowupRequest} a la entidad {@link Followup}.
     * El {@code id}, la {@code alert} y el {@code professor} se resuelven
     * en el service.
     *
     * @param request DTO con los datos de la acción
     * @return entidad {@link Followup} mapeada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alert", ignore = true)
    @Mapping(target = "professor", ignore = true)
    Followup toEntity(FollowupRequest request);

    /**
     * Convierte una entidad {@link Followup} a {@link FollowupResponse}.
     *
     * @param entity entidad de la acción de seguimiento
     * @return DTO de respuesta
     */
    @Mapping(target = "alertId", source = "alert.id")
    @Mapping(target = "professorName", expression = "java(mapProfessorName(entity))")
    FollowupResponse toResponse(Followup entity);

    /**
     * Construye el nombre completo del docente que realizó la acción.
     *
     * @param entity entidad de la acción
     * @return nombre y apellido del docente, o {@code null} si no hay docente asignado
     */
    default String mapProfessorName(Followup entity) {
        if (entity.getProfessor() == null) {
            return null;
        }
        return entity.getProfessor().getName() + " " + entity.getProfessor().getLastName();
    }
}
