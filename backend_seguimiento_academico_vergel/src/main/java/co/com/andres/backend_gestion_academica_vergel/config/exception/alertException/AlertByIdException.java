package co.com.andres.backend_gestion_academica_vergel.config.exception.alertException;

/**
 * Excepción que se lanza cuando se busca una alerta con un ID inexistente.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public class AlertByIdException extends RuntimeException {

    /**
     * Constructor por defecto con mensaje descriptivo del error.
     */
    public AlertByIdException() {
        super("NO SE ENCONTRÓ UNA ALERTA CON EL ID ESPECIFICADO");
    }
}
