package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.parentException.ParentByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.parentException.ParentWithEmailExistException;
import co.com.andres.backend_gestion_academica_vergel.mapper.ParentMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.ParentRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.ParentResponse;
import co.com.andres.backend_gestion_academica_vergel.repository.ParentRepository;
import co.com.andres.backend_gestion_academica_vergel.service.ParentService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de padres/acudientes.
 *
 * <p>Proporciona la lógica de negocio para todas las operaciones
 * relacionadas con padres, incluyendo encriptación de contraseña,
 * verificación de unicidad de email y manejo de excepciones personalizadas.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class ParentServiceImp implements ParentService {

    private final ParentRepository parentRepository;
    private final ParentMapper parentMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo padre/acudiente en el sistema.
     *
     * <p>Verifica que el email no esté registrado previamente, encripta
     * la contraseña y persiste el nuevo padre.</p>
     *
     * @param parentRequest datos del padre a crear
     * @return {@link ParentResponse} con la información del padre creado
     * @throws ParentWithEmailExistException si el email ya existe en el sistema
     * @since 2026
     */
    @Override
    public ParentResponse createParent(ParentRequest parentRequest) {
        var emailParent = parentRepository.findByEmail(parentRequest.email());
        if (emailParent.isPresent()) {
            throw new ParentWithEmailExistException();
        }

        var entity = parentMapper.toEntity(parentRequest);
        entity.setPassword(passwordEncoder.encode(parentRequest.password()));

        return parentMapper.toResponse(parentRepository.save(entity));
    }

    /**
     * Elimina un padre/acudiente del sistema por su identificador único.
     *
     * @param id identificador único del padre a eliminar
     * @throws ParentByIdException si no existe un padre con el ID especificado
     * @since 2026
     */
    @Override
    public void deleteParent(Long id) {
        var parent = parentRepository.findById(id)
                .orElseThrow(ParentByIdException::new);
        parentRepository.delete(parent);
    }

    /**
     * Obtiene la lista completa de padres registrados en el sistema.
     *
     * @return lista de padres transformada a DTOs de respuesta
     * @since 2026
     */
    @Override
    public List<ParentResponse> findAllParent() {
        return parentRepository.findAll()
                .stream()
                .map(parentMapper::toResponse)
                .toList();
    }

    /**
     * Busca un padre específico por su identificador único.
     *
     * @param id identificador único del padre
     * @return {@link ParentResponse} con la información del padre encontrado
     * @throws ParentByIdException si no existe un padre con el ID especificado
     * @since 2026
     */
    @Override
    public ParentResponse findByIdParent(Long id) {
        return parentRepository.findById(id)
                .map(parentMapper::toResponse)
                .orElseThrow(ParentByIdException::new);
    }

    /**
     * Actualiza la información de un padre existente.
     *
     * <p>Conserva el ID, los roles y la contraseña encriptada originales del padre.</p>
     *
     * @param id            identificador único del padre a actualizar
     * @param parentRequest nuevos datos del padre
     * @return {@link ParentResponse} con la información actualizada
     * @throws ParentByIdException si no existe un padre con el ID especificado
     * @since 2026
     */
    @Override
    public ParentResponse updateParent(Long id, ParentRequest parentRequest) {
        var existing = parentRepository.findById(id)
                .orElseThrow(ParentByIdException::new);

        var entity = parentMapper.toEntity(parentRequest);
        entity.setId(existing.getId());
        entity.setRoles(existing.getRoles());
        entity.setPassword(existing.getPassword());

        return parentMapper.toResponse(parentRepository.save(entity));
    }
}