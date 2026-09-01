package co.com.andres.backend_gestion_academica_vergel.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.ProfessorRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.ProfessorResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Professors;
import co.com.andres.backend_gestion_academica_vergel.model.shared.Role;

/**
 * Mapper para la conversión entre la entidad {@link Professors} y sus DTOs.
 *
 * Utiliza MapStruct para generar automáticamente la implementación
 * en tiempo de compilación.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Mapper(componentModel = "spring", imports = Set.class)
public interface ProfessorMapper {

    /**
     * Convierte un {@link ProfessorRequest} a la entidad {@link Professors}.
     * El {@code id}, {@code roles} y {@code subjects} se gestionan fuera del mapper.
     * Todo profesor nuevo se crea con el rol {@link Role#PROFESSOR} por defecto.
     *
     * @param request DTO con los datos del profesor
     * @return entidad {@link Professors} mapeada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", expression = "java(Set.of(Role.PROFESSOR.name()))")
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "password", ignore = true)
    Professors toEntity(ProfessorRequest request);

    /**
     * Convierte una entidad {@link Professors} a {@link ProfessorResponse}.
     * El campo {@code role} se obtiene a partir del conjunto {@code roles}
     * de la entidad mediante {@link #mapRole(Set)}.
     *
     * @param entity entidad del profesor
     * @return DTO de respuesta con la información del profesor
     */
    @Mapping(target = "role", expression = "java(mapRole(entity.getRoles()))")
    ProfessorResponse toResponse(Professors entity);

    /**
     * Actualiza una entidad {@link Professors} existente con los datos del request.
     * El {@code id}, {@code roles}, {@code professorNumber} y {@code subjects} se conservan.
     *
     * @param request DTO con los nuevos datos
     * @param entity  entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "professorNumber", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromRequest(ProfessorRequest request, @MappingTarget Professors entity);

    /**
     * Obtiene el rol principal de un profesor a partir de su conjunto de roles.
     *
     * <p>Toma el primer elemento del conjunto y lo convierte al enum {@link Role}.
     * Si el conjunto es nulo o está vacío, retorna {@code null}.</p>
     *
     * @param roles conjunto de roles del profesor almacenados como texto
     * @return el {@link Role} correspondiente, o {@code null} si no hay roles
     * @throws IllegalArgumentException si el valor almacenado no coincide con
     *         ningún valor de {@link Role}
     */
    default Role mapRole(Set<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        return Role.valueOf(roles.iterator().next());
    }
}