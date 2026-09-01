package co.com.andres.backend_gestion_academica_vergel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.backend_gestion_academica_vergel.model.entity.Note;

/**
 * Repositorio JPA para la gestión de notas académicas.
 *
 * <p>Proporciona operaciones CRUD básicas heredadas de {@link JpaRepository}
 * para la entidad {@link Note}.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface NoteRepository extends JpaRepository<Note, Long> {

    /**
     * Obtiene todas las notas asociadas a una matrícula.
     * Usado por el motor de alertas para calcular promedios.
     *
     * @param enrollmentId identificador de la matrícula
     * @return lista de notas de la matrícula
     */
    List<Note> findByEnrollmentsId(Long enrollmentId);
}