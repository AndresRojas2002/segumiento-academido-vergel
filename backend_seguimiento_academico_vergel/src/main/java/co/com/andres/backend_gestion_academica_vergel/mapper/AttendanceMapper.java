package co.com.andres.backend_gestion_academica_vergel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.AttendanceRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AttendanceResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Attendance;

/**
 * Mapper para la conversión entre la entidad {@link Attendance} y sus DTOs.
 *
 * Utiliza MapStruct para generar la implementación en tiempo de compilación.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    /**
     * Convierte un {@link AttendanceRequest} a la entidad {@link Attendance}.
     * El {@code id}, la {@code enrollments} y el {@code subject} se resuelven
     * en el service a partir de {@code enrollmentId} y {@code subjectId}.
     *
     * @param request DTO con los datos de la asistencia
     * @return entidad {@link Attendance} mapeada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "subject", ignore = true)
    Attendance toEntity(AttendanceRequest request);

    /**
     * Convierte una entidad {@link Attendance} a {@link AttendanceResponse}.
     *
     * @param entity entidad de la asistencia
     * @return DTO de respuesta con la información de la asistencia
     */
    @Mapping(target = "enrollmentId", source = "enrollments.id")
    @Mapping(target = "studentName", source = "enrollments.students.name")
    @Mapping(target = "studentLastName", source = "enrollments.students.lastName")
    @Mapping(target = "subjectName", source = "subject.nameSubject")
    AttendanceResponse toResponse(Attendance entity);
}
