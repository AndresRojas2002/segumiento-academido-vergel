package co.com.andres.backend_gestion_academica_vergel.config.exception.followupException;

/**
 * Excepción que se lanza cuando se busca una acción de seguimiento
 * con un ID inexistente.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public class FollowupByIdException extends RuntimeException {

    /**
     * Constructor por defecto con mensaje descriptivo del error.
     */
    public FollowupByIdException() {
        super("NO SE ENCONTRÓ UNA ACCIÓN DE SEGUIMIENTO CON EL ID ESPECIFICADO");
    }
}
