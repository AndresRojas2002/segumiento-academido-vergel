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

import co.com.andres.backend_gestion_academica_vergel.model.Dto.ParentRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.ParentResponse;
import co.com.andres.backend_gestion_academica_vergel.service.ParentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de padres o acudientes.
 *
 * Expone los endpoints necesarios para crear, consultar,
 * actualizar y eliminar padres del sistema académico.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
@Tag(name = "Padres", description = "Operaciones para la gestión de padres o acudientes")
public class ParentController {

    private final ParentService parentService;

    /**
     * Crea un nuevo padre o acudiente en el sistema.
     *
     * @param request datos del padre a crear
     * @return padre creado con estado HTTP 201
     */
    @Operation(summary = "Crear padre", description = "Registra un nuevo padre o acudiente en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Padre creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "409", description = "El email ya existe en el sistema")
    })
    @PostMapping
    public ResponseEntity<ParentResponse> createParent(@Valid @RequestBody ParentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(parentService.createParent(request));
    }

    /**
     * Obtiene la lista completa de padres registrados.
     *
     * @return lista de padres con estado HTTP 200
     */
    @Operation(summary = "Listar padres", description = "Retorna todos los padres o acudientes registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<ParentResponse>> getAllParents() {
        return ResponseEntity.ok(parentService.findAllParent());
    }

    /**
     * Busca un padre por su ID.
     *
     * @param id identificador único del padre
     * @return padre encontrado con estado HTTP 200
     */
    @Operation(summary = "Buscar por ID", description = "Retorna un padre o acudiente según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Padre encontrado"),
        @ApiResponse(responseCode = "404", description = "Padre no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ParentResponse> getById(
            @Parameter(description = "ID del padre") @PathVariable Long id) {
        return ResponseEntity.ok(parentService.findByIdParent(id));
    }

    /**
     * Actualiza la información de un padre existente.
     *
     * @param id      identificador único del padre
     * @param request nuevos datos del padre
     * @return padre actualizado con estado HTTP 200
     */
    @Operation(summary = "Actualizar padre", description = "Actualiza los datos de un padre o acudiente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Padre actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Padre no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ParentResponse> updateParent(
            @Parameter(description = "ID del padre") @PathVariable Long id,
            @Valid @RequestBody ParentRequest request) {
        return ResponseEntity.ok(parentService.updateParent(id, request));
    }

    /**
     * Elimina un padre del sistema.
     *
     * @param id identificador único del padre a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar padre", description = "Elimina un padre o acudiente del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Padre eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Padre no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(
            @Parameter(description = "ID del padre") @PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }
}