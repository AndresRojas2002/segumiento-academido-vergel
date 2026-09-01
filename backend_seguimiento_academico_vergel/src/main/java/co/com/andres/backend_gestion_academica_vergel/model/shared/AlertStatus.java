package co.com.andres.backend_gestion_academica_vergel.model.shared;

/**
 * Estado del ciclo de vida de una alerta temprana.
 *
 * Refleja el avance del docente en la atención de la situación
 * identificada por el sistema.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public enum AlertStatus {

    /** Alerta generada y aún sin atender. */
    OPEN,

    /** El docente está trabajando en la situación. */
    IN_PROGRESS,

    /** La situación fue atendida y cerrada. */
    RESOLVED
}
