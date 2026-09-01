package co.com.andres.backend_gestion_academica_vergel.service;

import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.AlertResponse;

/**
 * Contrato del motor de alertas tempranas.
 *
 * <p>Analiza las notas y la asistencia registradas y genera alertas
 * cuando detecta situaciones de riesgo. Es la funcionalidad que
 * diferencia al sistema de un gestor académico tradicional.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface AlertEngineService {

    /**
     * Evalúa una matrícula concreta y genera las alertas que correspondan.
     *
     * @param enrollmentId identificador de la matrícula a evaluar
     * @return lista de alertas nuevas generadas en esta evaluación
     */
    List<AlertResponse> evaluateEnrollment(Long enrollmentId);

    /**
     * Evalúa todas las matrículas activas del sistema.
     *
     * @return lista de todas las alertas nuevas generadas
     */
    List<AlertResponse> evaluateAll();
}
