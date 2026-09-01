package co.com.andres.backend_gestion_academica_vergel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.EnrollmentRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.EnrollmentResponse;
import co.com.andres.backend_gestion_academica_vergel.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de matrículas.
 *
 * Expone los endpoints necesarios para crear, consultar,
 * cancelar y eliminar matrículas del sistema académico.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Matrículas", description = "Operaciones para la gestión de matrículas")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    /**
     * Crea una nueva matrícula en el sistema.
     *
     * @param request datos de la matrícula a crear
     * @return matrícula creada con estado HTTP 201
     */
    @Operation(summary = "Crear matrícula", description = "Registra una nueva matrícula en el sistema con estado ACTIVE")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Matrícula creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<EnrollmentResponse> createEnrollment(@Valid @RequestBody EnrollmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enrollmentService.createEnrollments(request));
    }

    /**
     * Obtiene la lista completa de matrículas registradas.
     *
     * @return lista de matrículas con estado HTTP 200
     */
    @Operation(summary = "Listar matrículas", description = "Retorna todas las matrículas registradas en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    /**
     * Busca una matrícula por su ID.
     *
     * @param id identificador único de la matrícula
     * @return matrícula encontrada con estado HTTP 200
     */
    @Operation(summary = "Buscar por ID", description = "Retorna una matrícula según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Matrícula encontrada"),
        @ApiResponse(responseCode = "404", description = "Matrícula no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> getById(
            @Parameter(description = "ID de la matrícula") @PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getByIdEnrollments(id));
    }

    /**
     * Cancela una matrícula cambiando su estado a RETIRED.
     *
     * No elimina el registro, solo actualiza el estado.
     *
     * @param id identificador único de la matrícula a cancelar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Cancelar matrícula", description = "Cambia el estado de una matrícula a RETIRED sin eliminarla")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Matrícula cancelada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Matrícula no encontrada")
    })
    @PatchMapping("/{id}/retired")
    public ResponseEntity<Void> retiredEnrollment(
            @Parameter(description = "ID de la matrícula") @PathVariable Long id) {
        enrollmentService.retiredEnrollments(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina una matrícula del sistema.
     *
     * @param id identificador único de la matrícula a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar matrícula", description = "Elimina una matrícula del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Matrícula eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Matrícula no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(
            @Parameter(description = "ID de la matrícula") @PathVariable Long id) {
        enrollmentService.deleteEnrollments(id);
        return ResponseEntity.noContent().build();
    }
}