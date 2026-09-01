package co.com.andres.backend_gestion_academica_vergel.config.exception.subjectException;

/**
 * Excepción personalizada que se lanza cuando se intenta acceder a un curso
 * con un ID que no existe en el sistema.
 * 
 * Esta excepción extiende RuntimeException y se utiliza para manejar casos donde
 * se solicita información de un curso que no se encuentra registrado en la base de datos.
 * 
 * @author Andres
 * @version 1.0
 * @since 2024
 */
public class SubjectByIdException extends RuntimeException {

    /**
     * Constructor por defecto que inicializa la excepción con un mensaje
     * descriptivo sobre el error de la materia no encontrado.
     */
    public SubjectByIdException() {
        super("LA MATERIA CON ESTE ID, NO SE ENCUENTRA REGISTRADO");
    }

}
