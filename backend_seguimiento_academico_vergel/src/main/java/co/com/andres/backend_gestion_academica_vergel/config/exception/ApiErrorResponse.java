package co.com.andres.backend_gestion_academica_vergel.config.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Respuesta de error estandarizada para la API.
 *
 * <p>Encapsula la información necesaria para describir un error HTTP
 * de forma consistente en todos los endpoints de la aplicación.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
public class ApiErrorResponse {

    /**
     * Marca de tiempo del momento en que ocurrió el error.
     * Formateada en ISO 8601: {@code yyyy-MM-dd'T'HH:mm:ss}.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Código numérico del estado HTTP.
     * Ejemplo: 404, 409, 500.
     */
    private Integer status;

    /**
     * Descripción del estado HTTP.
     * Ejemplo: "Not Found", "Conflict", "Internal Server Error".
     */
    private String error;

    /**
     * Mensaje descriptivo del error específico que ocurrió.
     */
    private String message;

    /**
     * Ruta del endpoint que generó el error.
     * Ejemplo: {@code /api/students/99}.
     */
    private String path;

    /**
     * Construye una respuesta de error con la información básica.
     *
     * @param status  código de estado HTTP del error
     * @param message mensaje descriptivo del error
     * @param path    ruta de la solicitud que causó el error
     * @since 2026
     */
    public ApiErrorResponse(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}