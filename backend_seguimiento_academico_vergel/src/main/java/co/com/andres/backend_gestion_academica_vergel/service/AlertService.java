package co.com.andres.backend_gestion_academica_vergel.service;

import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.AlertResponse;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AlertStatusUpdateRequest;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertStatus;

/**
 * Contrato del servicio de consulta y gestión de alertas tempranas.
 *
 * <p>La generación de alertas la realiza {@link AlertEngineService};
 * este servicio se encarga de consultarlas y de actualizar su estado.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface AlertService {

    List<AlertResponse> findAll();

    AlertResponse findById(Long id);

    List<AlertResponse> findByStudent(Long studentId);

    List<AlertResponse> findByStatus(AlertStatus status);

    AlertResponse updateStatus(Long id, AlertStatusUpdateRequest request);

    void delete(Long id);
}
