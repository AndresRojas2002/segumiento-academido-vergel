package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.alertException.AlertByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.followupException.FollowupByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.professorException.ProfessorByIdException;
import co.com.andres.backend_gestion_academica_vergel.mapper.FollowupMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.FollowupRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.FollowupResponse;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertStatus;
import co.com.andres.backend_gestion_academica_vergel.repository.AlertRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.FollowupRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.ProfessorRepository;
import co.com.andres.backend_gestion_academica_vergel.service.FollowupService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de acciones de seguimiento.
 *
 * <p>Al registrar la primera acción sobre una alerta que está en estado
 * OPEN, la alerta pasa automáticamente a IN_PROGRESS, reflejando que el
 * docente ya está atendiendo la situación.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class FollowupServiceImp implements FollowupService {

    private final FollowupRepository followupRepository;
    private final AlertRepository alertRepository;
    private final ProfessorRepository professorRepository;
    private final FollowupMapper followupMapper;

    /**
     * Registra una nueva acción de seguimiento sobre una alerta.
     *
     * @param request datos de la acción
     * @return {@link FollowupResponse} con la información registrada
     * @throws AlertByIdException     si no existe la alerta indicada
     * @throws ProfessorByIdException si se indica un docente que no existe
     */
    @Override
    public FollowupResponse create(FollowupRequest request) {
        var alert = alertRepository.findById(request.alertId())
                .orElseThrow(AlertByIdException::new);

        var entity = followupMapper.toEntity(request);
        entity.setAlert(alert);

        if (request.professorId() != null) {
            var professor = professorRepository.findById(request.professorId())
                    .orElseThrow(ProfessorByIdException::new);
            entity.setProfessor(professor);
        }

        // Al iniciar el acompañamiento, la alerta abierta pasa a "en proceso".
        if (alert.getStatus() == AlertStatus.OPEN) {
            alert.setStatus(AlertStatus.IN_PROGRESS);
            alertRepository.save(alert);
        }

        return followupMapper.toResponse(followupRepository.save(entity));
    }

    @Override
    public List<FollowupResponse> findAll() {
        return followupRepository.findAll()
                .stream()
                .map(followupMapper::toResponse)
                .toList();
    }

    @Override
    public FollowupResponse findById(Long id) {
        return followupRepository.findById(id)
                .map(followupMapper::toResponse)
                .orElseThrow(FollowupByIdException::new);
    }

    @Override
    public List<FollowupResponse> findByAlert(Long alertId) {
        return followupRepository.findByAlertId(alertId)
                .stream()
                .map(followupMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        var followup = followupRepository.findById(id)
                .orElseThrow(FollowupByIdException::new);
        followupRepository.delete(followup);
    }
}
