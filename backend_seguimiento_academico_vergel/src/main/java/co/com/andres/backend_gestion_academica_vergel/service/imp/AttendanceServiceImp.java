package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.backend_gestion_academica_vergel.config.exception.attendanceException.AttendanceByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.enrollmentException.EnrollmentByIdException;
import co.com.andres.backend_gestion_academica_vergel.config.exception.subjectException.SubjectByIdException;
import co.com.andres.backend_gestion_academica_vergel.mapper.AttendanceMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AttendanceRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AttendanceResponse;
import co.com.andres.backend_gestion_academica_vergel.repository.AttendanceRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.EnrollmentsRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.SubjectRepository;
import co.com.andres.backend_gestion_academica_vergel.service.AttendanceService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de asistencia.
 *
 * <p>Resuelve la matrícula y la materia a partir de sus IDs antes de
 * persistir cada registro y expone las operaciones CRUD necesarias.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class AttendanceServiceImp implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentsRepository enrollmentsRepository;
    private final SubjectRepository subjectRepository;
    private final AttendanceMapper attendanceMapper;

    /**
     * Registra una nueva asistencia.
     *
     * @param request datos de la asistencia
     * @return {@link AttendanceResponse} con la información registrada
     * @throws EnrollmentByIdException si no existe la matrícula indicada
     * @throws SubjectByIdException    si no existe la materia indicada
     */
    @Override
    public AttendanceResponse create(AttendanceRequest request) {
        var enrollment = enrollmentsRepository.findById(request.enrollmentId())
                .orElseThrow(EnrollmentByIdException::new);
        var subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(SubjectByIdException::new);

        var entity = attendanceMapper.toEntity(request);
        entity.setEnrollments(enrollment);
        entity.setSubject(subject);

        return attendanceMapper.toResponse(attendanceRepository.save(entity));
    }

    @Override
    public List<AttendanceResponse> findAll() {
        return attendanceRepository.findAll()
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    @Override
    public AttendanceResponse findById(Long id) {
        return attendanceRepository.findById(id)
                .map(attendanceMapper::toResponse)
                .orElseThrow(AttendanceByIdException::new);
    }

    @Override
    public List<AttendanceResponse> findByEnrollment(Long enrollmentId) {
        return attendanceRepository.findByEnrollmentsId(enrollmentId)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    /**
     * Actualiza un registro de asistencia existente.
     *
     * @param id      identificador del registro
     * @param request nuevos datos
     * @return {@link AttendanceResponse} actualizado
     * @throws AttendanceByIdException si no existe el registro
     * @throws EnrollmentByIdException si no existe la matrícula indicada
     * @throws SubjectByIdException    si no existe la materia indicada
     */
    @Override
    public AttendanceResponse update(Long id, AttendanceRequest request) {
        var attendance = attendanceRepository.findById(id)
                .orElseThrow(AttendanceByIdException::new);

        var enrollment = enrollmentsRepository.findById(request.enrollmentId())
                .orElseThrow(EnrollmentByIdException::new);
        var subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(SubjectByIdException::new);

        attendance.setEnrollments(enrollment);
        attendance.setSubject(subject);
        attendance.setDate(request.date());
        attendance.setState(request.state());
        attendance.setPeriod(request.period());

        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Override
    public void delete(Long id) {
        var attendance = attendanceRepository.findById(id)
                .orElseThrow(AttendanceByIdException::new);
        attendanceRepository.delete(attendance);
    }
}
