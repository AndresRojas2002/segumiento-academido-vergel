package co.com.andres.backend_gestion_academica_vergel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.FollowupRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.FollowupResponse;
import co.com.andres.backend_gestion_academica_vergel.service.FollowupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para las acciones de seguimiento de las alertas.
 *
 * Permite al docente registrar y consultar las acciones realizadas
 * frente a cada alerta temprana.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/followups")
@RequiredArgsConstructor
@Tag(name = "Seguimiento", description = "Registro de acciones del docente frente a las alertas")
public class FollowupController {

    private final FollowupService followupService;

    /**
     * Registra una nueva acción de seguimiento.
     *
     * @param request datos de la acción
     * @return acción registrada con estado HTTP 201
     */
    @Operation(summary = "Registrar acción", description = "Registra una acción de seguimiento sobre una alerta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Acción registrada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Alerta o docente no encontrado")
    })
    @PostMapping
    public ResponseEntity<FollowupResponse> create(@Valid @RequestBody FollowupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(followupService.create(request));
    }

    /**
     * Lista todas las acciones de seguimiento registradas.
     *
     * @return lista de acciones
     */
    @Operation(summary = "Listar acciones", description = "Retorna todas las acciones de seguimiento registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<FollowupResponse>> getAll() {
        return ResponseEntity.ok(followupService.findAll());
    }

    /**
     * Busca una acción de seguimiento por su ID.
     *
     * @param id identificador de la acción
     * @return acción encontrada
     */
    @Operation(summary = "Buscar por ID", description = "Retorna una acción de seguimiento por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Acción encontrada"),
        @ApiResponse(responseCode = "404", description = "Acción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FollowupResponse> getById(
            @Parameter(description = "ID de la acción") @PathVariable Long id) {
        return ResponseEntity.ok(followupService.findById(id));
    }

    /**
     * Lista las acciones de seguimiento de una alerta.
     *
     * @param alertId identificador de la alerta
     * @return lista de acciones de la alerta
     */
    @Operation(summary = "Listar por alerta", description = "Retorna las acciones de seguimiento registradas sobre una alerta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping("/alert/{alertId}")
    public ResponseEntity<List<FollowupResponse>> getByAlert(
            @Parameter(description = "ID de la alerta") @PathVariable Long alertId) {
        return ResponseEntity.ok(followupService.findByAlert(alertId));
    }

    /**
     * Elimina una acción de seguimiento del sistema.
     *
     * @param id identificador de la acción a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar acción", description = "Elimina una acción de seguimiento por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Acción eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Acción no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la acción") @PathVariable Long id) {
        followupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
