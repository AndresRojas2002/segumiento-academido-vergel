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

import co.com.andres.backend_gestion_academica_vergel.model.Dto.StudentsRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.StudentsResponse;
import co.com.andres.backend_gestion_academica_vergel.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de estudiantes.
 *
 * Expone los endpoints necesarios para crear, consultar,
 * actualizar y eliminar estudiantes del sistema académico.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Estudiantes", description = "Operaciones para la gestión de estudiantes")
public class StudentController {

    private final StudentService studentService;

    /**
     * Crea un nuevo estudiante en el sistema.
     *
     * @param request datos del estudiante a crear
     * @return estudiante creado con estado HTTP 201
     */
    @Operation(summary = "Crear estudiante", description = "Registra un nuevo estudiante en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "409", description = "El email ya existe en el sistema")
    })
    @PostMapping
    public ResponseEntity<StudentsResponse> createStudent(@Valid @RequestBody StudentsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudents(request));
    }

    /**
     * Obtiene la lista completa de estudiantes registrados.
     *
     * @return lista de estudiantes con estado HTTP 200
     */
    @Operation(summary = "Listar estudiantes", description = "Retorna todos los estudiantes registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<StudentsResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * Busca un estudiante por su ID.
     *
     * @param id identificador único del estudiante
     * @return estudiante encontrado con estado HTTP 200
     */
    @Operation(summary = "Buscar por ID", description = "Retorna un estudiante según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
        @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentsResponse> getById(
            @Parameter(description = "ID del estudiante") @PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    /**
     * Busca estudiantes por nombre o apellido.
     *
     * @param text texto a buscar en nombre o apellido
     * @return lista de estudiantes que coinciden con la búsqueda
     */
    @Operation(summary = "Buscar por nombre o apellido", description = "Retorna estudiantes cuyo nombre o apellido contenga el texto indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    @GetMapping("/search")
    public ResponseEntity<List<StudentsResponse>> getByNameOrLastName(
            @Parameter(description = "Texto a buscar") @RequestParam String text) {
        return ResponseEntity.ok(studentService.getByNameOrLastName(text));
    }

    /**
     * Busca un estudiante por su correo electrónico.
     *
     * @param email correo electrónico del estudiante
     * @return lista de estudiantes con ese email
     */
    @Operation(summary = "Buscar por email", description = "Retorna el estudiante asociado al correo electrónico indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    @GetMapping("/email")
    public ResponseEntity<List<StudentsResponse>> getByEmail(
            @Parameter(description = "Correo electrónico del estudiante") @RequestParam String email) {
        return ResponseEntity.ok(studentService.getByEmail(email));
    }

    /**
     * Actualiza la información de un estudiante existente.
     *
     * @param id      identificador único del estudiante
     * @param request nuevos datos del estudiante
     * @return estudiante actualizado con estado HTTP 200
     */
    @Operation(summary = "Actualizar estudiante", description = "Actualiza los datos de un estudiante existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentsResponse> updateStudent(
            @Parameter(description = "ID del estudiante") @PathVariable Long id,
            @Valid @RequestBody StudentsRequest request) {
        return ResponseEntity.ok(studentService.updateStudents(id, request));
    }

    /**
     * Elimina un estudiante del sistema.
     *
     * @param id identificador único del estudiante a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar estudiante", description = "Elimina un estudiante del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Estudiante eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID del estudiante") @PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
