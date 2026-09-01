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

import co.com.andres.backend_gestion_academica_vergel.model.Dto.SubjectRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.SubjectResponse;
import co.com.andres.backend_gestion_academica_vergel.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de materias.
 *
 * Expone los endpoints necesarios para crear, consultar,
 * actualizar y eliminar materias del sistema académico.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Tag(name = "Materias", description = "Operaciones para la gestión de materias")
public class SubjectController {

    private final SubjectService subjectService;

    /**
     * Crea una nueva materia en el sistema.
     *
     * @param request datos de la materia a crear
     * @return materia creada con estado HTTP 201
     */
    @Operation(summary = "Crear materia", description = "Registra una nueva materia en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Materia creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<SubjectResponse> createSubject(@Valid @RequestBody SubjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subjectService.createSubject(request));
    }

    /**
     * Obtiene la lista completa de materias registradas.
     *
     * @return lista de materias con estado HTTP 200
     */
    @Operation(summary = "Listar materias", description = "Retorna todas las materias registradas en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubject());
    }

    /**
     * Busca una materia por su ID.
     *
     * @param id identificador único de la materia
     * @return materia encontrada con estado HTTP 200
     */
    @Operation(summary = "Buscar por ID", description = "Retorna una materia según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Materia encontrada"),
        @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getById(
            @Parameter(description = "ID de la materia") @PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getByIdSubject(id));
    }

    /**
     * Actualiza la información de una materia existente.
     *
     * @param id      identificador único de la materia
     * @param request nuevos datos de la materia
     * @return materia actualizada con estado HTTP 200
     */
    @Operation(summary = "Actualizar materia", description = "Actualiza los datos de una materia existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Materia actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponse> updateSubject(
            @Parameter(description = "ID de la materia") @PathVariable Long id,
            @Valid @RequestBody SubjectRequest request) {
        return ResponseEntity.ok(subjectService.updateSubject(id, request));
    }

    /**
     * Elimina una materia del sistema.
     *
     * @param id identificador único de la materia a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar materia", description = "Elimina una materia del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Materia eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(
            @Parameter(description = "ID de la materia") @PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}