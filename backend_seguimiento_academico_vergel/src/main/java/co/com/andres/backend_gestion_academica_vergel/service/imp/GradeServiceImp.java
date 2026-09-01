package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.gradeException.GradeByIdException;
import co.com.andres.backend_gestion_academica_vergel.mapper.GradeMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.GradeRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.GradeResponse;
import co.com.andres.backend_gestion_academica_vergel.repository.GradeRepository;
import co.com.andres.backend_gestion_academica_vergel.service.GradeService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de grados.
 * 
 * Esta clase proporciona la lógica de negocio para todas las operaciones
 * relacionadas con grados, incluyendo transformación de datos
 * y manejo de excepciones personalizadas.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class GradeServiceImp implements GradeService {

    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;

    /**
     * Crea un nuevo grado en el sistema.
     * 
     * Mapea los datos del DTO a la entidad y persiste el nuevo grado.
     * 
     * @param request Datos del grado a crear
     * @return GradeResponse con la información del grado creado
     */
    @Override
    public GradeResponse createGrade(GradeRequest request) {
        var entity = gradeMapper.toEntity(request);

        var newGrade = gradeRepository.save(entity);

        return gradeMapper.toResponse(newGrade);
    }

    /**
     * Elimina un grado del sistema por su identificador único.
     * 
     * @param id Identificador único del grado a eliminar
     * @throws GradeByIdException si no se encuentra un grado con el ID especificado
     */
    @Override
    public void deleteGrade(Long id) {
        var idGrade = gradeRepository.findById(id);
        if (!idGrade.isPresent()) {
            throw new GradeByIdException();
        }

        var grade = idGrade.get();
        gradeRepository.delete(grade);
    }

    /**
     * Obtiene la lista completa de grados registrados en el sistema.
     * 
     * @return Lista de grados transformada a DTOs de respuesta
     */
    @Override
    public List<GradeResponse> findAllGrade() {
        return gradeRepository.findAll()
        .stream()
        .map(gradeMapper::toResponse)
        .toList();
    }

    /**
     * Busca un grado específico por su identificador único.
     * 
     * @param id Identificador único del grado
     * @return GradeResponse con la información del grado encontrado
     * @throws GradeByIdException si no se encuentra un grado con el ID especificado
     */
    @Override
    public GradeResponse findByIdGrade(Long id) {
        return gradeRepository.findById(id)
        .map(gradeMapper::toResponse)
        .orElseThrow(() -> new GradeByIdException());
    }

    /**
     * Actualiza la información de un grado existente.
     * 
     * Verifica la existencia del grado antes de proceder con la actualización,
     * conservando su ID original.
     * 
     * @param id Identificador único del grado a actualizar
     * @param request Nuevos datos del grado
     * @return GradeResponse con la información actualizada del grado
     * @throws GradeByIdException si no se encuentra un grado con el ID especificado
     */
    @Override
    public GradeResponse updateGrade(Long id, GradeRequest request) {
        var idGrade = gradeRepository.findById(id);
        if (!idGrade.isPresent()) {
            throw new GradeByIdException();
        }

        // Se mapea el DTO a la entidad y se conserva el ID original del grado
        var entity = gradeMapper.toEntity(request);
        entity.setId(idGrade.get().getId());

        var update = gradeRepository.save(entity);
        return gradeMapper.toResponse(update);
    }

}
