package co.com.andres.backend_gestion_academica_vergel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.AlertResponse;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AlertStatusUpdateRequest;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertStatus;
import co.com.andres.backend_gestion_academica_vergel.service.AlertEngineService;
import co.com.andres.backend_gestion_academica_vergel.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para las alertas tempranas.
 *
 * Expone la ejecución del motor de alertas, la consulta de alertas por
 * estudiante o estado y la actualización de su estado a medida que el
 * docente atiende cada situación.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@Tag(name = "Alertas tempranas", description = "Motor de análisis y gestión de alertas de riesgo académico")
public class AlertController {

    private final AlertService alertService;
    private final AlertEngineService alertEngineService;

    /**
     * Ejecuta el motor de alertas sobre todas las matrículas.
     *
     * @return lista de alertas nuevas generadas
     */
    @Operation(summary = "Evaluar todo", description = "Analiza notas y asistencia de todas las matrículas y genera las alertas que correspondan")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evaluación ejecutada. Retorna las alertas nuevas generadas")
    })
    @PostMapping("/evaluate")
    public ResponseEntity<List<AlertResponse>> evaluateAll() {
        return ResponseEntity.ok(alertEngineService.evaluateAll());
    }

    /**
     * Ejecuta el motor de alertas sobre una matrícula específica.
     *
     * @param enrollmentId identificador de la matrícula
     * @return lista de alertas nuevas generadas para esa matrícula
     */
    @Operation(summary = "Evaluar una matrícula", description = "Analiza notas y asistencia de una matrícula y genera las alertas que correspondan")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evaluación ejecutada"),
        @ApiResponse(responseCode = "404", description = "Matrícula no encontrada")
    })
    @PostMapping("/evaluate/{enrollmentId}")
    public ResponseEntity<List<AlertResponse>> evaluateEnrollment(
            @Parameter(description = "ID de la matrícula") @PathVariable Long enrollmentId) {
        return ResponseEntity.ok(alertEngineService.evaluateEnrollment(enrollmentId));
    }

    /**
     * Lista todas las alertas registradas.
     *
     * @return lista de alertas
     */
    @Operation(summary = "Listar alertas", description = "Retorna todas las alertas registradas en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<AlertResponse>> getAll() {
        return ResponseEntity.ok(alertService.findAll());
    }

    /**
     * Busca una alerta por su ID.
     *
     * @param id identificador de la alerta
     * @return alerta encontrada
     */
    @Operation(summary = "Buscar por ID", description = "Retorna una alerta por su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Alerta encontrada"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlertResponse> getById(
            @Parameter(description = "ID de la alerta") @PathVariable Long id) {
        return ResponseEntity.ok(alertService.findById(id));
    }

    /**
     * Lista las alertas de un estudiante.
     *
     * @param studentId identificador del estudiante
     * @return lista de alertas del estudiante
     */
    @Operation(summary = "Listar por estudiante", description = "Retorna las alertas asociadas a un estudiante")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AlertResponse>> getByStudent(
            @Parameter(description = "ID del estudiante") @PathVariable Long studentId) {
        return ResponseEntity.ok(alertService.findByStudent(studentId));
    }

    /**
     * Lista las alertas por estado.
     *
     * @param status estado de la alerta (OPEN, IN_PROGRESS, RESOLVED)
     * @return lista de alertas en ese estado
     */
    @Operation(summary = "Listar por estado", description = "Retorna las alertas que se encuentran en el estado indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AlertResponse>> getByStatus(
            @Parameter(description = "Estado de la alerta") @PathVariable AlertStatus status) {
        return ResponseEntity.ok(alertService.findByStatus(status));
    }

    /**
     * Actualiza el estado de una alerta.
     *
     * @param id      identificador de la alerta
     * @param request nuevo estado
     * @return alerta actualizada
     */
    @Operation(summary = "Actualizar estado", description = "Cambia el estado de una alerta (OPEN, IN_PROGRESS, RESOLVED)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<AlertResponse> updateStatus(
            @Parameter(description = "ID de la alerta") @PathVariable Long id,
            @Valid @RequestBody AlertStatusUpdateRequest request) {
        return ResponseEntity.ok(alertService.updateStatus(id, request));
    }

    /**
     * Elimina una alerta del sistema.
     *
     * @param id identificador de la alerta a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar alerta", description = "Elimina una alerta del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Alerta eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la alerta") @PathVariable Long id) {
        alertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
