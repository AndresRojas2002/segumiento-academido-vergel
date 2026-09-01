package co.com.andres.backend_gestion_academica_vergel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.backend_gestion_academica_vergel.model.entity.Subject;

/**
 * Repositorio JPA para la gestión de materias.
 *
 * <p>Proporciona operaciones CRUD básicas heredadas de {@link JpaRepository}
 * para la entidad {@link Subject}.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}