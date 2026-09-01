package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.alertException.AlertByIdException;
import co.com.andres.backend_gestion_academica_vergel.mapper.AlertMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AlertResponse;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AlertStatusUpdateRequest;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertStatus;
import co.com.andres.backend_gestion_academica_vergel.repository.AlertRepository;
import co.com.andres.backend_gestion_academica_vergel.service.AlertService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de consulta y gestión de alertas.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class AlertServiceImp implements AlertService {

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    @Override
    public List<AlertResponse> findAll() {
        return alertRepository.findAll()
                .stream()
                .map(alertMapper::toResponse)
                .toList();
    }

    @Override
    public AlertResponse findById(Long id) {
        return alertRepository.findById(id)
                .map(alertMapper::toResponse)
                .orElseThrow(AlertByIdException::new);
    }

    @Override
    public List<AlertResponse> findByStudent(Long studentId) {
        return alertRepository.findByStudentId(studentId)
                .stream()
                .map(alertMapper::toResponse)
                .toList();
    }

    @Override
    public List<AlertResponse> findByStatus(AlertStatus status) {
        return alertRepository.findByStatus(status)
                .stream()
                .map(alertMapper::toResponse)
                .toList();
    }

    /**
     * Actualiza el estado de una alerta (por ejemplo de OPEN a IN_PROGRESS
     * o RESOLVED) a medida que el docente atiende la situación.
     *
     * @param id      identificador de la alerta
     * @param request nuevo estado
     * @return {@link AlertResponse} actualizada
     * @throws AlertByIdException si no existe la alerta
     */
    @Override
    public AlertResponse updateStatus(Long id, AlertStatusUpdateRequest request) {
        var alert = alertRepository.findById(id)
                .orElseThrow(AlertByIdException::new);
        alert.setStatus(request.status());
        return alertMapper.toResponse(alertRepository.save(alert));
    }

    @Override
    public void delete(Long id) {
        var alert = alertRepository.findById(id)
                .orElseThrow(AlertByIdException::new);
        alertRepository.delete(alert);
    }
}
