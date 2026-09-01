package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import java.time.LocalDate;

import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertSeverity;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertStatus;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertType;

/**
 * DTO de respuesta con la información de una alerta temprana.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 *
 * @param id              Identificador único de la alerta
 * @param studentId       Identificador del estudiante
 * @param studentName     Nombre del estudiante
 * @param studentLastName Apellido del estudiante
 * @param type            Tipo de alerta detectada
 * @param severity        Nivel de gravedad
 * @param status          Estado actual de la alerta
 * @param description     Mensaje descriptivo de la situación
 * @param metricValue     Valor de la métrica que disparó la alerta
 * @param generatedDate   Fecha de generación
 * @param period          Periodo académico evaluado
 */
public record AlertResponse(
        Long id,
        Long studentId,
        String studentName,
        String studentLastName,
        AlertType type,
        AlertSeverity severity,
        AlertStatus status,
        String description,
        Double metricValue,
        LocalDate generatedDate,
        String period) {
}
