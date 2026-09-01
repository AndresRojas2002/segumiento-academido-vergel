package co.com.andres.backend_gestion_academica_vergel.model.shared;

/**
 * Enumeración de estados posibles de una matrícula.
 * 
 * Define el ciclo de vida de una matrícula dentro
 * de la plataforma de gestión académica.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */

public enum EnrollmentsState {
    /** Matrícula activa y en curso. */
    ACTIVE,
    
    /** Matrícula retirada o cancelada. */
    RETIRED
}