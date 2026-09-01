package co.com.andres.backend_gestion_academica_vergel.config.exception.parentException;

public class ParentWithEmailExistException extends RuntimeException{

    /**
     * Constructor por defecto que inicializa la excepción con un mensaje
     * descriptivo sobre el error de duplicación de correo electrónico.
     */
    public ParentWithEmailExistException() {
        super("ESTE CORREO ELECTRONICO YA SE ENCUENTRA REGISTRADO");
    }

} 