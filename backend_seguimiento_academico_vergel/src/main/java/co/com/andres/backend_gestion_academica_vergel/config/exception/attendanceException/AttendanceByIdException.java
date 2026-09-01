package co.com.andres.backend_gestion_academica_vergel.config.exception.attendanceException;

/**
 * Excepción que se lanza cuando se busca una asistencia con un ID inexistente.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public class AttendanceByIdException extends RuntimeException {

    /**
     * Constructor por defecto con mensaje descriptivo del error.
     */
    public AttendanceByIdException() {
        super("NO SE ENCONTRÓ UN REGISTRO DE ASISTENCIA CON EL ID ESPECIFICADO");
    }
}
