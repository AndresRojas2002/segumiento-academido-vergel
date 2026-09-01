package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de solicitud para registrar una acción de seguimiento sobre una alerta.
 *
 * Si no se envía la fecha, se asigna automáticamente la fecha actual.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 *
 * @param alertId     ID de la alerta sobre la que se registra la acción
 * @param professorId ID del docente que realiza la acción (opcional)
 * @param action      Descripción de la acción realizada
 * @param result      Resultado o compromiso derivado (opcional)
 * @param date        Fecha de la acción (por defecto: fecha actual)
 */
public record FollowupRequest(

        @NotNull(message = "el id de la alerta no puede ser nulo")
        @JsonAlias({"alerta", "id_alerta"})
        Long alertId,

        @JsonAlias({"profesor", "id_profesor", "docente"})
        Long professorId,

        @NotBlank(message = "la acción no puede estar vacía")
        @JsonAlias({"accion", "descripcion"})
        String action,

        @JsonAlias({"resultado", "compromiso"})
        String result,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonAlias({"fecha"})
        LocalDate date) {

    public FollowupRequest {
        if (date == null) {
            date = LocalDate.now();
        }
    }
}
