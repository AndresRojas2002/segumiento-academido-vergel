package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.noteExeception.NoteByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.enrollmentException.EnrollmentByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.subjectException.SubjectByIdException;
import co.com.andres.backend_gestion_academica_vergel.mapper.NoteMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.NoteRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.NoteResponse;
import co.com.andres.backend_gestion_academica_vergel.repository.EnrollmentsRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.NoteRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.SubjectRepository;
import co.com.andres.backend_gestion_academica_vergel.service.NoteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteServiceImp implements NoteService {

    private final NoteRepository noteRepository;
    private final EnrollmentsRepository enrollmentsRepository;
    private final SubjectRepository subjectRepository;
    private final NoteMapper noteMapper;

    /**
     * Crea una nueva nota en el sistema.
     *
     * <p>Resuelve la matrícula ({@code enrollmentId}) y la materia
     * ({@code subjectId}) antes de persistir la nota.</p>
     *
     * @param request datos de la nota a crear
     * @return {@link NoteResponse} con la información de la nota creada
     * @throws EnrollmentByIdException si no existe la matrícula indicada
     * @throws SubjectByIdException si no existe la materia indicada
     */
    @Override
    public NoteResponse create(NoteRequest request) {
        var enrollment = enrollmentsRepository.findById(request.enrollmentId())
                .orElseThrow(EnrollmentByIdException::new);
        var subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(SubjectByIdException::new);

        var entity = noteMapper.toEntity(request);
        entity.setEnrollments(enrollment);
        entity.setSubject(subject);

        var newNote = noteRepository.save(entity);

        return noteMapper.toResponse(newNote);
    }

    @Override
    public void delete(Long id) {
        var idNote = noteRepository.findById(id);
        if (!idNote.isPresent()) {
            throw new NoteByIdException();
        }

        var note = idNote.get();
        noteRepository.delete(note);
    }

    @Override
    public List<NoteResponse> findAll() {
        return noteRepository.findAll()
                .stream()
                .map(noteMapper::toResponse)
                .toList();
    }

    @Override
    public NoteResponse findById(Long id) {
        return noteRepository.findById(id)
                .map(noteMapper::toResponse)
                .orElseThrow(() -> new NoteByIdException());
    }

    /**
     * Actualiza la información de una nota existente.
     *
     * <p>Resuelve nuevamente la matrícula y la materia a partir de los
     * IDs recibidos, permitiendo reasignarlas en la actualización.</p>
     *
     * @param id      identificador único de la nota a actualizar
     * @param request nuevos datos de la nota
     * @return {@link NoteResponse} con la información actualizada
     * @throws NoteByIdException si no se encuentra una nota con el ID especificado
     * @throws EnrollmentByIdException si no existe la matrícula indicada
     * @throws SubjectByIdException si no existe la materia indicada
     */
    @Override
    public NoteResponse update(Long id, NoteRequest request) {
        var idNote = noteRepository.findById(id);
        if (!idNote.isPresent()) {
            throw new NoteByIdException();
        }

        var enrollment = enrollmentsRepository.findById(request.enrollmentId())
                .orElseThrow(EnrollmentByIdException::new);
        var subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(SubjectByIdException::new);

        var entity = noteMapper.toEntity(request);
        entity.setId(idNote.get().getId());
        entity.setEnrollments(enrollment);
        entity.setSubject(subject);

        var update = noteRepository.save(entity);
        return noteMapper.toResponse(update);
    }
}