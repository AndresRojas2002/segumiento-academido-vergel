package co.com.andres.backend_gestion_academica_vergel.model.shared;

/**
 * Enumeración de estados posibles de un registro de asistencia.
 *
 * Representa la situación de un estudiante en una sesión de clase
 * y es la base para calcular el porcentaje de inasistencia usado
 * por el motor de alertas tempranas.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public enum AttendanceState {

    /** El estudiante asistió a la clase. */
    PRESENT,

    /** El estudiante no asistió a la clase (falta que cuenta como inasistencia). */
    ABSENT,

    /** El estudiante llegó tarde (se cuenta como asistencia). */
    LATE,

    /** Inasistencia justificada con excusa (no cuenta como inasistencia). */
    EXCUSED
}
