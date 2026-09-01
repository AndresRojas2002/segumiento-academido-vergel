package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.enrollmentException.EnrollmentByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.gradeException.GradeByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.studentException.StudentByIdException;
import co.com.andres.backend_gestion_academica_vergel.mapper.EnrollmentMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.EnrollmentRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.EnrollmentResponse;
import co.com.andres.backend_gestion_academica_vergel.model.shared.EnrollmentsState;
import co.com.andres.backend_gestion_academica_vergel.repository.EnrollmentsRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.GradeRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.StudentRepository;
import co.com.andres.backend_gestion_academica_vergel.service.EnrollmentService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de matrículas.
 *
 * <p>Proporciona la lógica de negocio para todas las operaciones
 * relacionadas con matrículas, incluyendo asignación de estudiante y grado,
 * cambio de estado y manejo de excepciones personalizadas.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class EnrollmentServiceImp implements EnrollmentService {

    private final EnrollmentsRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    /**
     * Crea una nueva matrícula en el sistema.
     *
     * <p>Busca el estudiante y el grado por sus IDs, los asigna a la entidad
     * y persiste la nueva matrícula.</p>
     *
     * @param enrollmentRequest datos de la matrícula a crear
     * @return {@link EnrollmentResponse} con la información de la matrícula creada
     * @throws StudentByIdException    si no existe un estudiante con el ID especificado
     * @throws GradeByIdException      si no existe un grado con el ID especificado
     * @since 2026
     */
    @Override
    public EnrollmentResponse createEnrollments(EnrollmentRequest enrollmentRequest) {
        var entity = enrollmentMapper.toEntity(enrollmentRequest);

        var student = studentRepository.findById(enrollmentRequest.students())
                .orElseThrow(StudentByIdException::new);
        var grade = gradeRepository.findById(enrollmentRequest.idGrado())
                .orElseThrow(GradeByIdException::new);

        entity.setStudents(student);
        entity.setGrade(grade);

        var saved = enrollmentRepository.save(entity);
        return enrollmentMapper.toResponse(
                enrollmentRepository.findById(saved.getId()).orElseThrow());
    }

    /**
     * Cancela una matrícula existente cambiando su estado a {@code RETIRED}.
     *
     * <p>No elimina el registro de la base de datos, solo actualiza su estado.</p>
     *
     * @param id identificador único de la matrícula a cancelar
     * @throws EnrollmentByIdException si no existe una matrícula con el ID especificado
     * @since 2026
     */
    @Override
    public void retiredEnrollments(Long id) {
        var enrollment = enrollmentRepository.findById(id)
                .orElseThrow(EnrollmentByIdException::new);
        enrollment.setEnrollmentsState(EnrollmentsState.RETIRED);
        enrollmentRepository.save(enrollment);
    }

    /**
     * Elimina una matrícula del sistema por su identificador único.
     *
     * @param id identificador único de la matrícula a eliminar
     * @throws EnrollmentByIdException si no existe una matrícula con el ID especificado
     * @since 2026
     */
    @Override
    public void deleteEnrollments(Long id) {
        var enrollment = enrollmentRepository.findById(id)
                .orElseThrow(EnrollmentByIdException::new);
        enrollmentRepository.delete(enrollment);
    }

    /**
     * Obtiene la lista completa de matrículas registradas en el sistema.
     *
     * @return lista de matrículas transformada a DTOs de respuesta
     * @since 2026
     */
    @Override
    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(enrollmentMapper::toResponse)
                .toList();
    }

    /**
     * Busca una matrícula específica por su identificador único.
     *
     * @param id identificador único de la matrícula
     * @return {@link EnrollmentResponse} con la información de la matrícula encontrada
     * @throws EnrollmentByIdException si no existe una matrícula con el ID especificado
     * @since 2026
     */
    @Override
    public EnrollmentResponse getByIdEnrollments(Long id) {
        return enrollmentRepository.findById(id)
                .map(enrollmentMapper::toResponse)
                .orElseThrow(EnrollmentByIdException::new);
    }
}