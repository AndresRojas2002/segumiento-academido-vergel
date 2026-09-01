package co.com.andres.backend_gestion_academica_vergel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.backend_gestion_academica_vergel.model.entity.Grade;

/**
 * Repositorio JPA para la gestión de grados académicos.
 *
 * <p>Proporciona operaciones CRUD básicas heredadas de {@link JpaRepository}
 * para la entidad {@link Grade}.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface GradeRepository extends JpaRepository<Grade, Long> {

}