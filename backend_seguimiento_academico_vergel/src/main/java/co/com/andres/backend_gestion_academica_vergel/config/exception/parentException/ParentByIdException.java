package co.com.andres.backend_gestion_academica_vergel.config.exception.parentException;

public class ParentByIdException extends RuntimeException {
    

     /**
     * Constructor por defecto que inicializa la excepción con un mensaje
     * descriptivo sobre la no existencia del estudiante con el ID especificado.
     */
     public ParentByIdException(){
        super("PADRE CON ESE ID, NO ENCONTRADO");
    }
}
