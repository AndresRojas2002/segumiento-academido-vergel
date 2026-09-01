package co.com.andres.backend_gestion_academica_vergel.config.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.com.andres.backend_gestion_academica_vergel.config.exception.alertException.AlertByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.attendanceException.AttendanceByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.authenticate.InvalidCredentialsException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.enrollmentException.EnrollmentByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.followupException.FollowupByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.gradeException.GradeByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.noteExeception.NoteByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.parentException.ParentByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.parentException.ParentWithEmailExistException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.professorException.ProfessorByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.professorException.ProfessorWithEmailExistException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.studentException.StudentByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.studentException.StudentWithEmailExistException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.subjectException.SubjectByIdException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Manejador global de excepciones de la API.
 *
 * <p>Intercepta todas las excepciones lanzadas por los controladores
 * y retorna una respuesta estandarizada usando {@link ApiErrorResponse}.
 * Agrupa los errores por código HTTP:</p>
 * <ul>
 *   <li>{@code 400} — errores de validación de entrada</li>
 *   <li>{@code 401} — credenciales inválidas</li>
 *   <li>{@code 404} — recursos no encontrados</li>
 *   <li>{@code 409} — conflictos de unicidad (email duplicado)</li>
 *   <li>{@code 500} — errores internos no controlados</li>
 * </ul>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── 404 Not Found ─────────────────────────────────────────────────────────

    /**
     * Maneja el caso en que no se encuentra un estudiante por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(StudentByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentById(
            StudentByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que no se encuentra un profesor por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(ProfessorByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleProfessorById(
            ProfessorByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que no se encuentra un acudiente por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(ParentByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleParentById(
            ParentByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que no se encuentra una matrícula por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(EnrollmentByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleEnrollmentById(
            EnrollmentByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que no se encuentra un grado por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(GradeByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleGradeById(
            GradeByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que no se encuentra una nota por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(NoteByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleNoteById(
            NoteByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que no se encuentra una materia por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(SubjectByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleSubjectById(
            SubjectByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que no se encuentra un registro de asistencia por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(AttendanceByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleAttendanceById(
            AttendanceByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que no se encuentra una alerta por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(AlertByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleAlertById(
            AlertByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que no se encuentra una acción de seguimiento por su ID.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 404 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(FollowupByIdException.class)
    public ResponseEntity<ApiErrorResponse> handleFollowupById(
            FollowupByIdException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    // ── 409 Conflict ──────────────────────────────────────────────────────────

    /**
     * Maneja el caso en que se intenta registrar un estudiante con un email duplicado.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 409 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(StudentWithEmailExistException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentEmailExist(
            StudentWithEmailExistException ex, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que se intenta registrar un profesor con un email duplicado.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 409 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(ProfessorWithEmailExistException.class)
    public ResponseEntity<ApiErrorResponse> handleProfessorEmailExist(
            ProfessorWithEmailExistException ex, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Maneja el caso en que se intenta registrar un acudiente con un email duplicado.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 409 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(ParentWithEmailExistException.class)
    public ResponseEntity<ApiErrorResponse> handleParentEmailExist(
            ParentWithEmailExistException ex, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    // ── 401 Unauthorized ──────────────────────────────────────────────────────

    /**
     * Maneja el caso en que las credenciales de autenticación son inválidas.
     *
     * @param ex      excepción lanzada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 401 y detalle del error
     * @since 2026
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException ex, HttpServletRequest request) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI());
    }

    // ── 400 Bad Request ───────────────────────────────────────────────────────

    /**
     * Maneja los errores de validación de los campos del request.
     *
     * <p>Extrae el primer error de campo encontrado y lo retorna como mensaje.</p>
     *
     * @param ex      excepción de validación lanzada por {@code @Valid}
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 400 y el primer error de validación encontrado
     * @since 2026
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación");
        return build(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
    }

    // ── 409 Conflict (integridad referencial) ─────────────────────────────────

    /**
     * Maneja el intento de eliminar un registro que tiene información asociada
     * (por ejemplo un grado con matrículas, o una matrícula con notas).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException ex, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT,
                "No se puede eliminar este registro porque tiene información asociada "
                + "(notas, matrículas, asistencias u otros). Elimina primero los registros relacionados.",
                request.getRequestURI());
    }

    // ── 500 Internal Server Error ─────────────────────────────────────────────

    /**
     * Maneja cualquier excepción no controlada por los handlers anteriores.
     *
     * @param ex      excepción no controlada
     * @param request solicitud HTTP que causó el error
     * @return respuesta con código 500 y mensaje del error
     * @since 2026
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneral(
            Exception ex, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI());
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    /**
     * Construye una {@link ResponseEntity} con {@link ApiErrorResponse}.
     *
     * @param status  código HTTP del error
     * @param message mensaje descriptivo del error
     * @param path    ruta de la solicitud que causó el error
     * @return respuesta HTTP con el cuerpo de error estandarizado
     * @since 2026
     */
    private ResponseEntity<ApiErrorResponse> build(HttpStatus status, String message, String path) {
        return ResponseEntity.status(status)
                .body(new ApiErrorResponse(status, message, path));
    }
}