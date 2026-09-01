package co.com.andres.backend_gestion_academica_vergel.service;

import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.FollowupRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.FollowupResponse;

/**
 * Contrato del servicio de acciones de seguimiento de alertas.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface FollowupService {

    FollowupResponse create(FollowupRequest request);

    List<FollowupResponse> findAll();

    FollowupResponse findById(Long id);

    List<FollowupResponse> findByAlert(Long alertId);

    void delete(Long id);
}
