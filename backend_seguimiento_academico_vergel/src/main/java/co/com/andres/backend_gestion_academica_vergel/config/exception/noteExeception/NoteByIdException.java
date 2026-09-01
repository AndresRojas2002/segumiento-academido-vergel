package co.com.andres.backend_gestion_academica_vergel.config.exception.noteExeception;

public class NoteByIdException extends RuntimeException{
    
     /**
     * Constructor por defecto que inicializa la excepción con un mensaje
     * descriptivo sobre la no existencia del estudiante con el ID especificado.
     */
     public NoteByIdException(){
        super("NOTA CON ESE ID, NO ENCONTRADO");
    }
}
