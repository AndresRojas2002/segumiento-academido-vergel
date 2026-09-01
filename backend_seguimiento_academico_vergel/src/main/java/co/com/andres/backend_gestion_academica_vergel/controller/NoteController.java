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

import co.com.andres.backend_gestion_academica_vergel.model.Dto.NoteRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.NoteResponse;
import co.com.andres.backend_gestion_academica_vergel.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de notas académicas.
 *
 * Expone los endpoints necesarios para crear, consultar,
 * actualizar y eliminar notas del sistema académico.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Notas", description = "Operaciones para la gestión de notas académicas")
public class NoteController {

    private final NoteService noteService;

    /**
     * Crea una nueva nota en el sistema.
     *
     * @param request datos de la nota a crear
     * @return nota creada con estado HTTP 201
     */
    @Operation(summary = "Crear nota", description = "Registra una nueva nota académica en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Nota creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(noteService.create(request));
    }

    /**
     * Obtiene la lista completa de notas registradas.
     *
     * @return lista de notas con estado HTTP 200
     */
    @Operation(summary = "Listar notas", description = "Retorna todas las notas registradas en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        return ResponseEntity.ok(noteService.findAll());
    }

    /**
     * Busca una nota por su ID.
     *
     * @param id identificador único de la nota
     * @return nota encontrada con estado HTTP 200
     */
    @Operation(summary = "Buscar por ID", description = "Retorna una nota según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nota encontrada"),
        @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getById(
            @Parameter(description = "ID de la nota") @PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    /**
     * Actualiza una nota existente.
     *
     * @param id      identificador único de la nota
     * @param request nuevos datos de la nota
     * @return nota actualizada con estado HTTP 200
     */
    @Operation(summary = "Actualizar nota", description = "Actualiza los datos de una nota existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nota actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @Parameter(description = "ID de la nota") @PathVariable Long id,
            @Valid @RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.update(id, request));
    }

    /**
     * Elimina una nota del sistema.
     *
     * @param id identificador único de la nota a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar nota", description = "Elimina una nota del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Nota eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @Parameter(description = "ID de la nota") @PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}