package co.com.andres.backend_gestion_academica_vergel.model.shared;

/**
 * Nivel de gravedad de una alerta temprana.
 *
 * Permite priorizar la atención del docente frente a los casos
 * más críticos.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public enum AlertSeverity {

    /** Riesgo bajo: la situación está cerca del umbral. */
    LOW,

    /** Riesgo medio: la situación supera el umbral de forma clara. */
    MEDIUM,

    /** Riesgo alto: la situación es crítica y requiere atención inmediata. */
    HIGH
}
