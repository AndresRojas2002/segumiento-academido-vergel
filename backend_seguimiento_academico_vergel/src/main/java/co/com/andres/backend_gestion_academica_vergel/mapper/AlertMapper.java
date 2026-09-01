package co.com.andres.backend_gestion_academica_vergel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.AlertResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Alert;

/**
 * Mapper para la conversión de la entidad {@link Alert} a su DTO de respuesta.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Mapper(componentModel = "spring")
public interface AlertMapper {

    /**
     * Convierte una entidad {@link Alert} a {@link AlertResponse}.
     *
     * @param entity entidad de la alerta
     * @return DTO de respuesta con la información de la alerta
     */
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", source = "student.name")
    @Mapping(target = "studentLastName", source = "student.lastName")
    AlertResponse toResponse(Alert entity);
}
