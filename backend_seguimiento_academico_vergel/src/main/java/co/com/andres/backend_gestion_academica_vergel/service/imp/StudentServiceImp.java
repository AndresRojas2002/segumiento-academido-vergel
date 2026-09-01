package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.parentException.ParentByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.studentException.StudentByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.studentException.StudentWithEmailExistException;
import co.com.andres.backend_gestion_academica_vergel.mapper.StudentMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.StudentsRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.StudentsResponse;
import co.com.andres.backend_gestion_academica_vergel.repository.ParentRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.StudentRepository;
import co.com.andres.backend_gestion_academica_vergel.service.StudentService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de estudiantes.
 *
 * <p>Proporciona la lógica de negocio para todas las operaciones
 * relacionadas con estudiantes, incluyendo encriptación de contraseña,
 * verificación de unicidad de email, resolución del acudiente
 * y manejo de excepciones personalizadas.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService {

    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo estudiante en el sistema.
     *
     * <p>Verifica que el email no esté registrado previamente, resuelve el
     * acudiente ({@code parentId}), encripta la contraseña y persiste
     * el nuevo estudiante.</p>
     *
     * @param studentRequest datos del estudiante a crear
     * @return {@link StudentsResponse} con la información del estudiante creado
     * @throws StudentWithEmailExistException si el email ya existe en el sistema
     * @throws ParentByIdException si no existe un acudiente con el {@code parentId} indicado
     * @since 2026
     */
    @Override
    public StudentsResponse createStudents(StudentsRequest studentRequest) {
        var emailStudent = studentRepository.findByEmail(studentRequest.email());
        if (emailStudent.isPresent()) {
            throw new StudentWithEmailExistException();
        }

        var parent = parentRepository.findById(studentRequest.parentId())
                .orElseThrow(ParentByIdException::new);

        var entity = studentMapper.toEntity(studentRequest);
        entity.setParent(parent);
        entity.setPassword(passwordEncoder.encode(studentRequest.password()));

        return studentMapper.toResponse(studentRepository.save(entity));
    }

    /**
     * Obtiene la lista completa de estudiantes registrados en el sistema.
     *
     * @return lista de estudiantes transformada a DTOs de respuesta
     * @since 2026
     */
    @Override
    public List<StudentsResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    /**
     * Busca un estudiante específico por su identificador único.
     *
     * @param id identificador único del estudiante
     * @return {@link StudentsResponse} con la información del estudiante encontrado
     * @throws StudentByIdException si no existe un estudiante con el ID especificado
     * @since 2026
     */
    @Override
    public StudentsResponse getById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toResponse)
                .orElseThrow(StudentByIdException::new);
    }

    /**
     * Actualiza la información de un estudiante existente.
     *
     * <p>Conserva el ID, los roles y la contraseña encriptada originales del
     * estudiante, y resuelve nuevamente el acudiente a partir del {@code parentId}
     * recibido (permite reasignar el acudiente en la actualización).</p>
     *
     * @param id             identificador único del estudiante a actualizar
     * @param studentRequest nuevos datos del estudiante
     * @return {@link StudentsResponse} con la información actualizada
     * @throws StudentByIdException si no existe un estudiante con el ID especificado
     * @throws ParentByIdException si no existe un acudiente con el {@code parentId} indicado
     * @since 2026
     */
    @Override
    public StudentsResponse updateStudents(Long id, StudentsRequest studentRequest) {
        var existing = studentRepository.findById(id)
                .orElseThrow(StudentByIdException::new);

        var parent = parentRepository.findById(studentRequest.parentId())
                .orElseThrow(ParentByIdException::new);

        var entity = studentMapper.toEntity(studentRequest);
        entity.setId(existing.getId());
        entity.setRoles(existing.getRoles());
        entity.setPassword(existing.getPassword());
        entity.setParent(parent);

        return studentMapper.toResponse(studentRepository.save(entity));
    }

    /**
     * Elimina un estudiante del sistema por su identificador único.
     *
     * @param id identificador único del estudiante a eliminar
     * @throws StudentByIdException si no existe un estudiante con el ID especificado
     * @since 2026
     */
    @Override
    public void deleteById(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(StudentByIdException::new);
        studentRepository.delete(student);
    }

    /**
     * Busca estudiantes cuyo nombre o apellido contenga el texto especificado.
     *
     * @param text texto a buscar en nombre o apellido (insensible a mayúsculas)
     * @return lista de estudiantes que coinciden con la búsqueda
     * @since 2026
     */
    @Override
    public List<StudentsResponse> getByNameOrLastName(String text) {
        return studentRepository
                .findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(text, text)
                .stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    /**
     * Busca estudiantes por email.
     *
     * @param email email a buscar
     * @return lista de estudiantes que coinciden con el email
     * @since 2026
     */
    @Override
    public List<StudentsResponse> getByEmail(String email) {
        return studentRepository.findByEmail(email)
                .stream()
                .map(studentMapper::toResponse)
                .toList();
    }
}