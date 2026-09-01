package co.com.andres.backend_gestion_academica_vergel.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.backend_gestion_academica_vergel.model.entity.Alert;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertStatus;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertType;

/**
 * Repositorio JPA para la gestión de alertas tempranas.
 *
 * <p>Expone consultas derivadas para listar alertas por estudiante o
 * estado y para evitar la duplicación de alertas abiertas.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface AlertRepository extends JpaRepository<Alert, Long> {

    /**
     * Lista las alertas de un estudiante.
     *
     * @param studentId identificador del estudiante
     * @return alertas asociadas al estudiante
     */
    List<Alert> findByStudentId(Long studentId);

    /**
     * Lista las alertas por estado.
     *
     * @param status estado de la alerta
     * @return alertas en el estado indicado
     */
    List<Alert> findByStatus(AlertStatus status);

    /**
     * Verifica si ya existe una alerta de un tipo para un estudiante
     * en alguno de los estados indicados. Se usa para no duplicar
     * alertas que aún están abiertas o en proceso.
     *
     * @param studentId identificador del estudiante
     * @param type      tipo de alerta
     * @param statuses  estados a considerar
     * @return {@code true} si existe al menos una alerta que cumpla la condición
     */
    boolean existsByStudentIdAndTypeAndStatusIn(
            Long studentId, AlertType type, Collection<AlertStatus> statuses);
}
