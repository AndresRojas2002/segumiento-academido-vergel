package co.com.andres.backend_gestion_academica_vergel.model.shared;

/**
 * Enumeración de roles disponibles en el sistema.
 * 
 * Define los tipos de usuarios que pueden autenticarse y operar
 * dentro de la plataforma de gestión académica.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public enum Role {
    /** Profesor con acceso a gestión de materias y notas. */
    PROFESSOR,
    /** Estudiante con acceso a su información académica. */
    STUDENT,
    /** Padre o acudiente con acceso a la información de su hijo. */
    PARENT
}