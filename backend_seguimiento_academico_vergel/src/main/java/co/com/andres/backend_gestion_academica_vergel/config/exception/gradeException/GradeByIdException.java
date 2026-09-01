package co.com.andres.backend_gestion_academica_vergel.config.exception.gradeException;

public class GradeByIdException extends RuntimeException{
     /**
     * Constructor por defecto que inicializa la excepción con un mensaje
     * descriptivo sobre la no existencia del estudiante con el ID especificado.
     */
     public GradeByIdException(){
        super("GRADO CON ESE ID, NO ENCONTRADO");
}
}