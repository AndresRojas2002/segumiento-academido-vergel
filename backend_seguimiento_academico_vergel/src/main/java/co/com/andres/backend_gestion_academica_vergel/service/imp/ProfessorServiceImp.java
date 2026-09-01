package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.professorException.ProfessorByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.professorException.ProfessorWithEmailExistException;
import co.com.andres.backend_gestion_academica_vergel.mapper.ProfessorMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.ProfessorRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.ProfessorResponse;
import co.com.andres.backend_gestion_academica_vergel.repository.ProfessorRepository;
import co.com.andres.backend_gestion_academica_vergel.service.ProfessorService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de profesores.
 *
 * <p>Proporciona la lógica de negocio para todas las operaciones
 * relacionadas con profesores, incluyendo encriptación de contraseña,
 * verificación de unicidad de email y manejo de excepciones personalizadas.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class ProfessorServiceImp implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo profesor en el sistema.
     *
     * <p>Verifica que el email no esté registrado previamente, encripta
     * la contraseña y persiste el nuevo profesor.</p>
     *
     * @param professorRequest datos del profesor a crear
     * @return {@link ProfessorResponse} con la información del profesor creado
     * @throws ProfessorWithEmailExistException si el email ya existe en el sistema
     * @since 2026
     */
    @Override
    public ProfessorResponse createProfessor(ProfessorRequest professorRequest) {
        var emailProfessor = professorRepository.findByEmail(professorRequest.email());
        if (emailProfessor.isPresent()) {
            throw new ProfessorWithEmailExistException();
        }

        var entity = professorMapper.toEntity(professorRequest);
        entity.setPassword(passwordEncoder.encode(professorRequest.password()));

        return professorMapper.toResponse(professorRepository.save(entity));
    }

    /**
     * Elimina un profesor del sistema por su identificador único.
     *
     * @param id identificador único del profesor a eliminar
     * @throws ProfessorByIdException si no existe un profesor con el ID especificado
     * @since 2026
     */
    @Override
    public void deleteById(Long id) {
        var professor = professorRepository.findById(id)
                .orElseThrow(ProfessorByIdException::new);
        professorRepository.delete(professor);
    }

    /**
     * Obtiene la lista completa de profesores registrados en el sistema.
     *
     * @return lista de profesores transformada a DTOs de respuesta
     * @since 2026
     */
    @Override
    public List<ProfessorResponse> getAllProfessor() {
        return professorRepository.findAll()
                .stream()
                .map(professorMapper::toResponse)
                .toList();
    }

    /**
     * Busca profesores por su correo electrónico.
     *
     * @param email correo electrónico a buscar
     * @return lista de profesores que coinciden con el email especificado
     * @since 2026
     */
    @Override
    public List<ProfessorResponse> getByEmail(String email) {
        return professorRepository.findByEmail(email)
                .stream()
                .map(professorMapper::toResponse)
                .toList();
    }

    /**
     * Busca un profesor específico por su identificador único.
     *
     * @param id identificador único del profesor
     * @return {@link ProfessorResponse} con la información del profesor encontrado
     * @throws ProfessorByIdException si no existe un profesor con el ID especificado
     * @since 2026
     */
    @Override
    public ProfessorResponse getById(Long id) {
        return professorRepository.findById(id)
                .map(professorMapper::toResponse)
                .orElseThrow(ProfessorByIdException::new);
    }

    /**
     * Busca profesores cuyo nombre o apellido contenga el texto especificado.
     *
     * @param text texto a buscar en nombre o apellido (insensible a mayúsculas)
     * @return lista de profesores que coinciden con la búsqueda
     * @since 2026
     */
    @Override
    public List<ProfessorResponse> getByNameOrLastName(String text) {
        return professorRepository
                .findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(text, text)
                .stream()
                .map(professorMapper::toResponse)
                .toList();
    }

    /**
     * Actualiza la información de un profesor existente.
     *
     * <p>Conserva el ID, los roles y la contraseña encriptada originales del profesor.</p>
     *
     * @param id               identificador único del profesor a actualizar
     * @param professorRequest nuevos datos del profesor
     * @return {@link ProfessorResponse} con la información actualizada
     * @throws ProfessorByIdException si no existe un profesor con el ID especificado
     * @since 2026
     */
    @Override
    public ProfessorResponse updateProfessor(Long id, ProfessorRequest professorRequest) {
        var existing = professorRepository.findById(id)
                .orElseThrow(ProfessorByIdException::new);

        var entity = professorMapper.toEntity(professorRequest);
        entity.setId(existing.getId());
        entity.setRoles(existing.getRoles());
        entity.setPassword(existing.getPassword());

        return professorMapper.toResponse(professorRepository.save(entity));
    }
}