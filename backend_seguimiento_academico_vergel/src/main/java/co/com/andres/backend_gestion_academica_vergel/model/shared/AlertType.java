package co.com.andres.backend_gestion_academica_vergel.model.shared;

/**
 * Tipos de alerta temprana que el sistema puede generar.
 *
 * Cada tipo corresponde a una situación de riesgo detectada por el
 * motor de reglas al analizar notas y asistencia del estudiante.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public enum AlertType {

    /** Bajo rendimiento académico: promedio general por debajo del umbral. */
    LOW_PERFORMANCE,

    /** Dificultad en varias materias: dos o más materias perdidas. */
    MULTIPLE_SUBJECTS_AT_RISK,

    /** Inasistencia: porcentaje de asistencia por debajo del umbral. */
    LOW_ATTENDANCE
}
