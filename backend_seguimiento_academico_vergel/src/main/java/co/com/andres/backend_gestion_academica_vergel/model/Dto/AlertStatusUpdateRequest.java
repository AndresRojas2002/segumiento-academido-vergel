package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertStatus;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de solicitud para actualizar el estado de una alerta.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 *
 * @param status Nuevo estado de la alerta (OPEN, IN_PROGRESS, RESOLVED)
 */
public record AlertStatusUpdateRequest(

        @NotNull(message = "el estado no puede ser nulo")
        @JsonAlias({"estado"})
        AlertStatus status) {
}
