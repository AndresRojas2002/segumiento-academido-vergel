package co.com.andres.backend_gestion_academica_vergel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.backend_gestion_academica_vergel.model.entity.Enrollments;

/**
 * Repositorio JPA para la gestión de matrículas.
 *
 * <p>Proporciona operaciones CRUD básicas heredadas de {@link JpaRepository}
 * para la entidad {@link Enrollments}.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface EnrollmentsRepository extends JpaRepository<Enrollments, Long> {

}
