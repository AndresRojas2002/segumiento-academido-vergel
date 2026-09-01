package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import java.time.LocalDate;

/**
 * DTO de respuesta con la información de una acción de seguimiento.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 *
 * @param id            Identificador único de la acción
 * @param alertId       Identificador de la alerta asociada
 * @param professorName Nombre del docente que realizó la acción
 * @param action        Descripción de la acción realizada
 * @param result        Resultado o compromiso derivado
 * @param date          Fecha de la acción
 */
public record FollowupResponse(
        Long id,
        Long alertId,
        String professorName,
        String action,
        String result,
        LocalDate date) {
}
