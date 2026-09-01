package co.com.andres.backend_gestion_academica_vergel.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.StudentsRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.StudentsResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Students;
import co.com.andres.backend_gestion_academica_vergel.model.shared.Role;

/**
 * Mapper para la conversión entre la entidad {@link Students} y sus DTOs.
 *
 * Utiliza MapStruct para generar automáticamente la implementación
 * en tiempo de compilación.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Mapper(componentModel = "spring", imports = Set.class)
public interface StudentMapper {

    /**
     * Convierte un {@link StudentsRequest} a la entidad {@link Students}.
     * El {@code id}, {@code roles} y {@code password} se gestionan fuera del mapper.
     * Todo estudiante nuevo se crea con el rol {@link Role#STUDENT} por defecto.
     *
     * @param request DTO con los datos del estudiante
     * @return entidad {@link Students} mapeada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", expression = "java(Set.of(Role.STUDENT.name()))")
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "parent", ignore = true)
    Students toEntity(StudentsRequest request);

    /**
     * Convierte una entidad {@link Students} a {@link StudentsResponse}.
     * El campo {@code role} se obtiene del conjunto {@code roles} de la entidad
     * mediante {@link ProfessorMapper#mapRole(Set)}-equivalente {@link #mapRole(Set)}.
     * El campo {@code nameParent} se calcula de forma segura si el estudiante
     * tiene un acudiente ({@code parent}) asociado.
     *
     * @param entity entidad del estudiante
     * @return DTO de respuesta con la información del estudiante
     */
    @Mapping(target = "nameParent", expression = "java(mapParentName(entity))")
    @Mapping(target = "role", expression = "java(mapRole(entity.getRoles()))")
    StudentsResponse toResponse(Students entity);

    /**
     * Actualiza una entidad {@link Students} existente con los datos del request.
     * El {@code id}, {@code roles}, {@code studentNumber}, {@code userName}
     * y {@code password} se conservan sin modificación.
     *
     * @param request DTO con los nuevos datos
     * @param entity  entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "studentNumber", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "parent", ignore = true)
    void updateEntityFromRequest(StudentsRequest request, @MappingTarget Students entity);

    /**
     * Obtiene el rol principal de un estudiante a partir de su conjunto de roles.
     *
     * @param roles conjunto de roles del estudiante almacenados como texto
     * @return el {@link Role} correspondiente, o {@code null} si no hay roles
     */
    default Role mapRole(Set<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        return Role.valueOf(roles.iterator().next());
    }

    /**
     * Construye el nombre completo del acudiente del estudiante.
     *
     * @param entity entidad del estudiante
     * @return nombre y apellido del acudiente, o {@code null} si el estudiante
     *         no tiene un acudiente asociado
     */
    default String mapParentName(Students entity) {
        if (entity.getParent() == null) {
            return null;
        }
        return entity.getParent().getName() + " " + entity.getParent().getLastName();
    }
}