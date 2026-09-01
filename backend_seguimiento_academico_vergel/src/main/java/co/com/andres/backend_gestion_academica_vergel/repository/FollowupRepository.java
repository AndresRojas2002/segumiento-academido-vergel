package co.com.andres.backend_gestion_academica_vergel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.backend_gestion_academica_vergel.model.entity.Followup;

/**
 * Repositorio JPA para las acciones de seguimiento de las alertas.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface FollowupRepository extends JpaRepository<Followup, Long> {

    /**
     * Lista las acciones de seguimiento registradas sobre una alerta.
     *
     * @param alertId identificador de la alerta
     * @return acciones de seguimiento asociadas
     */
    List<Followup> findByAlertId(Long alertId);
}
