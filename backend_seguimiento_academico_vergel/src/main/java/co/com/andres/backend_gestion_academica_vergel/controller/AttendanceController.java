package co.com.andres.backend_gestion_academica_vergel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.AttendanceRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AttendanceResponse;
import co.com.andres.backend_gestion_academica_vergel.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de asistencia.
 *
 * Expone los endpoints necesarios para registrar, consultar,
 * actualizar y eliminar registros de asistencia.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
@Tag(name = "Asistencia", description = "Operaciones para el registro de asistencia de estudiantes")
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * Registra una nueva asistencia en el sistema.
     *
     * @param request datos de la asistencia a registrar
     * @return asistencia registrada con estado HTTP 201
     */
    @Operation(summary = "Registrar asistencia", description = "Registra la asistencia de un estudiante en una materia y fecha")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Asistencia registrada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Matrícula o materia no encontrada")
    })
    @PostMapping
    public ResponseEntity<AttendanceResponse> create(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.create(request));
    }

    /**
     * Obtiene la lista completa de registros de asistencia.
     *
     * @return lista de asistencias con estado HTTP 200
     */
    @Operation(summary = "Listar asistencias", description = "Retorna todos los registros de asistencia")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getAll() {
        return ResponseEntity.ok(attendanceService.findAll());
    }

    /**
     * Busca un registro de asistencia por su ID.
     *
     * @param id identificador único del registro
     * @return asistencia encontrada con estado HTTP 200
     */
    @Operation(summary = "Buscar por ID", description = "Retorna un registro de asistencia por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> getById(
            @Parameter(description = "ID de la asistencia") @PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.findById(id));
    }

    /**
     * Lista las asistencias de una matrícula específica.
     *
     * @param enrollmentId identificador de la matrícula
     * @return lista de asistencias de la matrícula
     */
    @Operation(summary = "Listar por matrícula", description = "Retorna las asistencias registradas para una matrícula")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<AttendanceResponse>> getByEnrollment(
            @Parameter(description = "ID de la matrícula") @PathVariable Long enrollmentId) {
        return ResponseEntity.ok(attendanceService.findByEnrollment(enrollmentId));
    }

    /**
     * Actualiza un registro de asistencia existente.
     *
     * @param id      identificador del registro
     * @param request nuevos datos
     * @return asistencia actualizada con estado HTTP 200
     */
    @Operation(summary = "Actualizar asistencia", description = "Actualiza un registro de asistencia existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Asistencia actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Registro, matrícula o materia no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponse> update(
            @Parameter(description = "ID de la asistencia") @PathVariable Long id,
            @Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.update(id, request));
    }

    /**
     * Elimina un registro de asistencia del sistema.
     *
     * @param id identificador del registro a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar asistencia", description = "Elimina un registro de asistencia por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Asistencia eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la asistencia") @PathVariable Long id) {
        attendanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
