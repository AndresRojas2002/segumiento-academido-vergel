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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.ProfessorRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.ProfessorResponse;
import co.com.andres.backend_gestion_academica_vergel.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de profesores.
 *
 * Expone los endpoints necesarios para crear, consultar,
 * actualizar y eliminar profesores del sistema académico.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
@Tag(name = "Profesores", description = "Operaciones para la gestión de profesores")
public class ProfessorController {

    private final ProfessorService professorService;

    /**
     * Crea un nuevo profesor en el sistema.
     *
     * @param request datos del profesor a crear
     * @return profesor creado con estado HTTP 201
     */
    @Operation(summary = "Crear profesor", description = "Registra un nuevo profesor en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Profesor creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "409", description = "El email ya existe en el sistema")
    })
    @PostMapping
    public ResponseEntity<ProfessorResponse> createProfessor(@Valid @RequestBody ProfessorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(professorService.createProfessor(request));
    }

    /**
     * Obtiene la lista completa de profesores registrados.
     *
     * @return lista de profesores con estado HTTP 200
     */
    @Operation(summary = "Listar profesores", description = "Retorna todos los profesores registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<ProfessorResponse>> getAllProfessors() {
        return ResponseEntity.ok(professorService.getAllProfessor());
    }

    /**
     * Busca un profesor por su ID.
     *
     * @param id identificador único del profesor
     * @return profesor encontrado con estado HTTP 200
     */
    @Operation(summary = "Buscar por ID", description = "Retorna un profesor según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profesor encontrado"),
        @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponse> getById(
            @Parameter(description = "ID del profesor") @PathVariable Long id) {
        return ResponseEntity.ok(professorService.getById(id));
    }

    /**
     * Busca profesores por nombre o apellido.
     *
     * @param text texto a buscar en nombre o apellido
     * @return lista de profesores que coinciden con la búsqueda
     */
    @Operation(summary = "Buscar por nombre o apellido", description = "Retorna profesores cuyo nombre o apellido contenga el texto indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProfessorResponse>> getByNameOrLastName(
            @Parameter(description = "Texto a buscar") @RequestParam String text) {
        return ResponseEntity.ok(professorService.getByNameOrLastName(text));
    }

    /**
     * Busca un profesor por su correo electrónico.
     *
     * @param email correo electrónico del profesor
     * @return lista de profesores con ese email
     */
    @Operation(summary = "Buscar por email", description = "Retorna el profesor asociado al correo electrónico indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    @GetMapping("/email")
    public ResponseEntity<List<ProfessorResponse>> getByEmail(
            @Parameter(description = "Correo electrónico del profesor") @RequestParam String email) {
        return ResponseEntity.ok(professorService.getByEmail(email));
    }

    /**
     * Actualiza la información de un profesor existente.
     *
     * @param id      identificador único del profesor
     * @param request nuevos datos del profesor
     * @return profesor actualizado con estado HTTP 200
     */
    @Operation(summary = "Actualizar profesor", description = "Actualiza los datos de un profesor existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profesor actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponse> updateProfessor(
            @Parameter(description = "ID del profesor") @PathVariable Long id,
            @Valid @RequestBody ProfessorRequest request) {
        return ResponseEntity.ok(professorService.updateProfessor(id, request));
    }

    /**
     * Elimina un profesor del sistema.
     *
     * @param id identificador único del profesor a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar profesor", description = "Elimina un profesor del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Profesor eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID del profesor") @PathVariable Long id) {
        professorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}