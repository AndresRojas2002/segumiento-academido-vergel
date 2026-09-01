package co.com.andres.backend_gestion_academica_vergel.config.exception.professorException;

/**
 * Excepción personalizada que se lanza cuando se intenta buscar, actualizar o eliminar
 * un profesor con un ID que no existe en el sistema.
 * 
 * Esta excepción extiende RuntimeException para indicar que es una excepción
 * no verificada que puede ser manejada de forma opcional.
 * 
 * @author Andres
 * @version 1.0
 * @since 2024
 */
public class ProfessorByIdException extends RuntimeException {

    /**
     * Constructor por defecto que inicializa la excepción con un mensaje
     * descriptivo del error.
     */
    public ProfessorByIdException() {
        super("EL PROFESOR CON ESTE ID NO EXISTE EN EL SISTEMA");
    }
    
}
    

