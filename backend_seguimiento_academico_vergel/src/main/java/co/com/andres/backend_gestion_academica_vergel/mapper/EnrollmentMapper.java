package co.com.andres.backend_gestion_academica_vergel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.EnrollmentRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.EnrollmentResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Enrollments;
import co.com.andres.backend_gestion_academica_vergel.model.shared.EnrollmentsState;

/**
 * Mapper para la conversión entre la entidad {@link Enrollments} y sus DTOs.
 *
 * Utiliza MapStruct para generar automáticamente la implementación
 * en tiempo de compilación.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Mapper(componentModel = "spring", imports = EnrollmentsState.class)
public interface EnrollmentMapper {

    /**
     * Convierte un {@link EnrollmentRequest} a la entidad {@link Enrollments}.
     * El estado se inicializa como ACTIVE. El {@code id} y {@code notes}
     * se gestionan fuera del mapper.
     *
     * @param request DTO con los datos de la matrícula
     * @return entidad {@link Enrollments} mapeada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "enrollmentsState", expression = "java(EnrollmentsState.ACTIVE)")
    Enrollments toEntity(EnrollmentRequest request);

    /**
     * Convierte una entidad {@link Enrollments} a {@link EnrollmentResponse}.
     *
     * @param entity entidad de la matrícula
     * @return DTO de respuesta con la información de la matrícula
     */
    @Mapping(target = "nameStudents", source = "students.name")
    @Mapping(target = "lastNameStudent", source = "students.lastName")
    @Mapping(target = "nameGrade", source = "grade.nameGrade")
    EnrollmentResponse toResponse(Enrollments entity);
}