package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.gradeException.GradeByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.professorException.ProfessorByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.subjectException.SubjectByIdException;
import co.com.andres.backend_gestion_academica_vergel.mapper.SubjectMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.SubjectRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.SubjectResponse;
import co.com.andres.backend_gestion_academica_vergel.repository.GradeRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.ProfessorRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.SubjectRepository;
import co.com.andres.backend_gestion_academica_vergel.service.SubjectService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de materias.
 *
 * <p>Proporciona la lógica de negocio para todas las operaciones
 * relacionadas con materias, incluyendo asignación de profesor y grado,
 * y manejo de excepciones personalizadas.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class SubjectServiceImp implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final ProfessorRepository professorRepository;
    private final GradeRepository gradeRepository;

    /**
     * Crea una nueva materia en el sistema.
     *
     * <p>Busca el profesor y el grado por sus IDs, los asigna a la entidad
     * y persiste la nueva materia.</p>
     *
     * @param subjectRequest datos de la materia a crear
     * @return {@link SubjectResponse} con la información de la materia creada
     * @throws ProfessorByIdException si no existe un profesor con el ID especificado
     * @throws GradeByIdException     si no existe un grado con el ID especificado
     * @since 2026
     */
    @Override
    public SubjectResponse createSubject(SubjectRequest subjectRequest) {
        var entity = subjectMapper.toEntity(subjectRequest);

        var professor = professorRepository.findById(subjectRequest.professorId())
                .orElseThrow(ProfessorByIdException::new);
        var grade = gradeRepository.findById(subjectRequest.gradeId())
                .orElseThrow(GradeByIdException::new);

        entity.setProfessors(professor);
        entity.setGrade(grade);

        return subjectMapper.toResponse(subjectRepository.save(entity));
    }

    /**
     * Actualiza la información de una materia existente.
     *
     * <p>Conserva el ID original y reasigna el profesor y grado según el request.</p>
     *
     * @param id             identificador único de la materia a actualizar
     * @param subjectRequest nuevos datos de la materia
     * @return {@link SubjectResponse} con la información actualizada
     * @throws SubjectByIdException   si no existe una materia con el ID especificado
     * @throws ProfessorByIdException si no existe un profesor con el ID especificado
     * @throws GradeByIdException     si no existe un grado con el ID especificado
     * @since 2026
     */
    @Override
    public SubjectResponse updateSubject(Long id, SubjectRequest subjectRequest) {
        var existing = subjectRepository.findById(id)
                .orElseThrow(SubjectByIdException::new);

        var entity = subjectMapper.toEntity(subjectRequest);
        entity.setId(existing.getId());

        var professor = professorRepository.findById(subjectRequest.professorId())
                .orElseThrow(ProfessorByIdException::new);
        var grade = gradeRepository.findById(subjectRequest.gradeId())
                .orElseThrow(GradeByIdException::new);

        entity.setProfessors(professor);
        entity.setGrade(grade);

        return subjectMapper.toResponse(subjectRepository.save(entity));
    }

    /**
     * Elimina una materia del sistema por su identificador único.
     *
     * @param id identificador único de la materia a eliminar
     * @throws SubjectByIdException si no existe una materia con el ID especificado
     * @since 2026
     */
    @Override
    public void deleteSubject(Long id) {
        var subject = subjectRepository.findById(id)
                .orElseThrow(SubjectByIdException::new);
        subjectRepository.delete(subject);
    }

    /**
     * Obtiene la lista completa de materias registradas en el sistema.
     *
     * @return lista de materias transformada a DTOs de respuesta
     * @since 2026
     */
    @Override
    public List<SubjectResponse> getAllSubject() {
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::toResponse)
                .toList();
    }

    /**
     * Busca una materia específica por su identificador único.
     *
     * @param id identificador único de la materia
     * @return {@link SubjectResponse} con la información de la materia encontrada
     * @throws SubjectByIdException si no existe una materia con el ID especificado
     * @since 2026
     */
    @Override
    public SubjectResponse getByIdSubject(Long id) {
        return subjectRepository.findById(id)
                .map(subjectMapper::toResponse)
                .orElseThrow(SubjectByIdException::new);
    }
}