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

import co.com.andres.backend_gestion_academica_vergel.model.Dto.GradeRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.GradeResponse;
import co.com.andres.backend_gestion_academica_vergel.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de grados.
 *
 * Expone los endpoints necesarios para crear, consultar,
 * actualizar y eliminar grados del sistema académico.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
@Tag(name = "Grados", description = "Operaciones para la gestión de grados")
public class GradeController {

    private final GradeService gradeService;

    /**
     * Crea un nuevo grado en el sistema.
     *
     * @param request datos del grado a crear
     * @return grado creado con estado HTTP 201
     */
    @Operation(summary = "Crear grado", description = "Registra un nuevo grado en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Grado creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<GradeResponse> createGrade(@Valid @RequestBody GradeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gradeService.createGrade(request));
    }

    /**
     * Obtiene la lista completa de grados registrados.
     *
     * @return lista de grados con estado HTTP 200
     */
    @Operation(summary = "Listar grados", description = "Retorna todos los grados registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<GradeResponse>> getAllGrades() {
        return ResponseEntity.ok(gradeService.findAllGrade());
    }

    /**
     * Busca un grado por su ID.
     *
     * @param id identificador único del grado
     * @return grado encontrado con estado HTTP 200
     */
    @Operation(summary = "Buscar por ID", description = "Retorna un grado según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Grado encontrado"),
        @ApiResponse(responseCode = "404", description = "Grado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GradeResponse> getById(
            @Parameter(description = "ID del grado") @PathVariable Long id) {
        return ResponseEntity.ok(gradeService.findByIdGrade(id));
    }

    /**
     * Actualiza la información de un grado existente.
     *
     * @param id      identificador único del grado
     * @param request nuevos datos del grado
     * @return grado actualizado con estado HTTP 200
     */
    @Operation(summary = "Actualizar grado", description = "Actualiza los datos de un grado existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Grado actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Grado no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GradeResponse> updateGrade(
            @Parameter(description = "ID del grado") @PathVariable Long id,
            @Valid @RequestBody GradeRequest request) {
        return ResponseEntity.ok(gradeService.updateGrade(id, request));
    }

    /**
     * Elimina un grado del sistema.
     *
     * @param id identificador único del grado a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar grado", description = "Elimina un grado del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Grado eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Grado no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(
            @Parameter(description = "ID del grado") @PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}