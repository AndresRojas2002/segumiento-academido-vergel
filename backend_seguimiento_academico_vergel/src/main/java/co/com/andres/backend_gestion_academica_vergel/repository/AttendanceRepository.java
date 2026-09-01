package co.com.andres.backend_gestion_academica_vergel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.backend_gestion_academica_vergel.model.entity.Attendance;

/**
 * Repositorio JPA para la gestión de registros de asistencia.
 *
 * <p>Además de las operaciones CRUD heredadas de {@link JpaRepository},
 * expone consultas derivadas usadas por el motor de alertas.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Obtiene todos los registros de asistencia de una matrícula.
     *
     * @param enrollmentId identificador de la matrícula
     * @return lista de registros de asistencia asociados
     */
    List<Attendance> findByEnrollmentsId(Long enrollmentId);
}
