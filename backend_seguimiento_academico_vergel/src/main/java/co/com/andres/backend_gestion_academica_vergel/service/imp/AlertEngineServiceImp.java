package co.com.andres.backend_gestion_academica_vergel.service.imp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.andres.backend_gestion_academica_vergel.config.exception.enrollmentException.EnrollmentByIdException;
import co.com.andres.backend_gestion_academica_vergel.mapper.AlertMapper;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AlertResponse;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Alert;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Enrollments;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Note;
import co.com.andres.backend_gestion_academica_vergel.model.entity.Students;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertSeverity;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertStatus;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertType;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AttendanceState;
import co.com.andres.backend_gestion_academica_vergel.repository.AlertRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.AttendanceRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.EnrollmentsRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.NoteRepository;
import co.com.andres.backend_gestion_academica_vergel.service.AlertEngineService;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del motor de alertas tempranas.
 *
 * <p>Aplica tres reglas configurables sobre las notas y la asistencia de
 * cada matrícula:</p>
 * <ul>
 *   <li><b>Bajo rendimiento</b>: promedio general por debajo de la nota mínima.</li>
 *   <li><b>Varias materias en riesgo</b>: número de materias perdidas mayor o igual al umbral.</li>
 *   <li><b>Inasistencia</b>: porcentaje de asistencia por debajo del mínimo.</li>
 * </ul>
 *
 * <p>Para no saturar al docente, no se crea una alerta nueva de un tipo si
 * el estudiante ya tiene una alerta de ese mismo tipo en estado OPEN o
 * IN_PROGRESS.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class AlertEngineServiceImp implements AlertEngineService {

    private final EnrollmentsRepository enrollmentsRepository;
    private final NoteRepository noteRepository;
    private final AttendanceRepository attendanceRepository;
    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    /** Nota mínima aprobatoria (escala 0.0 a 5.0). Por defecto 3.0. */
    @Value("${alert.threshold.passing-grade:3.0}")
    private double passingGrade;

    /** Número mínimo de materias perdidas para disparar la alerta. Por defecto 2. */
    @Value("${alert.threshold.min-subjects-at-risk:2}")
    private int minSubjectsAtRisk;

    /** Porcentaje mínimo de asistencia exigido. Por defecto 80.0. */
    @Value("${alert.threshold.min-attendance-percent:80.0}")
    private double minAttendancePercent;

    /** Estados que se consideran "abiertos" para evitar alertas duplicadas. */
    private static final List<AlertStatus> OPEN_STATUSES =
            List.of(AlertStatus.OPEN, AlertStatus.IN_PROGRESS);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<AlertResponse> evaluateEnrollment(Long enrollmentId) {
        var enrollment = enrollmentsRepository.findById(enrollmentId)
                .orElseThrow(EnrollmentByIdException::new);
        return evaluate(enrollment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<AlertResponse> evaluateAll() {
        var generated = new ArrayList<AlertResponse>();
        for (var enrollment : enrollmentsRepository.findAll()) {
            generated.addAll(evaluate(enrollment));
        }
        return generated;
    }

    /**
     * Ejecuta las tres reglas sobre una matrícula y persiste las alertas
     * que correspondan.
     *
     * @param enrollment matrícula a evaluar
     * @return alertas nuevas generadas
     */
    private List<AlertResponse> evaluate(Enrollments enrollment) {
        var student = enrollment.getStudents();
        if (student == null) {
            return List.of();
        }

        var notes = noteRepository.findByEnrollmentsId(enrollment.getId());
        var attendances = attendanceRepository.findByEnrollmentsId(enrollment.getId());
        var period = resolvePeriod(notes);

        var newAlerts = new ArrayList<Alert>();

        // ── Regla 1 y 2: rendimiento a partir de las notas ────────────────
        var valuedNotes = notes.stream()
                .filter(n -> n.getValue() != null)
                .toList();

        if (!valuedNotes.isEmpty()) {
            double overallAverage = valuedNotes.stream()
                    .mapToDouble(Note::getValue)
                    .average()
                    .orElse(0.0);

            // Promedio por materia (solo notas con materia asignada).
            Map<Long, Double> averageBySubject = valuedNotes.stream()
                    .filter(n -> n.getSubject() != null)
                    .collect(Collectors.groupingBy(
                            n -> n.getSubject().getId(),
                            Collectors.averagingDouble(Note::getValue)));

            long subjectsAtRisk = averageBySubject.values().stream()
                    .filter(avg -> avg < passingGrade)
                    .count();

            // Regla 1: bajo rendimiento general.
            if (overallAverage < passingGrade) {
                var description = String.format(
                        "Bajo rendimiento académico: el estudiante %s %s tiene un promedio general de %.2f, "
                                + "por debajo de la nota mínima aprobatoria (%.1f).",
                        student.getName(), student.getLastName(), overallAverage, passingGrade);
                addIfAbsent(newAlerts, student, AlertType.LOW_PERFORMANCE,
                        performanceSeverity(overallAverage),
                        description, round(overallAverage), period);
            }

            // Regla 2: dificultad en varias materias.
            if (subjectsAtRisk >= minSubjectsAtRisk) {
                var description = String.format(
                        "Dificultad en varias materias: el estudiante %s %s presenta %d materia(s) con promedio "
                                + "por debajo de %.1f.",
                        student.getName(), student.getLastName(), subjectsAtRisk, passingGrade);
                addIfAbsent(newAlerts, student, AlertType.MULTIPLE_SUBJECTS_AT_RISK,
                        subjectsSeverity(subjectsAtRisk),
                        description, (double) subjectsAtRisk, period);
            }
        }

        // ── Regla 3: inasistencia ─────────────────────────────────────────
        if (!attendances.isEmpty()) {
            long absences = attendances.stream()
                    .filter(a -> a.getState() == AttendanceState.ABSENT)
                    .count();
            double attendancePercent = (attendances.size() - absences) * 100.0 / attendances.size();

            if (attendancePercent < minAttendancePercent) {
                var description = String.format(
                        "Inasistencia: el estudiante %s %s tiene un %.1f%% de asistencia (%d falta(s) de %d clases), "
                                + "por debajo del mínimo exigido (%.1f%%).",
                        student.getName(), student.getLastName(), attendancePercent,
                        absences, attendances.size(), minAttendancePercent);
                addIfAbsent(newAlerts, student, AlertType.LOW_ATTENDANCE,
                        attendanceSeverity(attendancePercent),
                        description, round(attendancePercent), period);
            }
        }

        if (newAlerts.isEmpty()) {
            return List.of();
        }

        return alertRepository.saveAll(newAlerts).stream()
                .map(alertMapper::toResponse)
                .toList();
    }

    /**
     * Crea la alerta y la agrega a la lista solo si el estudiante no tiene
     * ya una alerta del mismo tipo abierta o en proceso.
     */
    private void addIfAbsent(List<Alert> target, Students student, AlertType type,
                             AlertSeverity severity, String description,
                             Double metricValue, String period) {

        boolean alreadyOpen = alertRepository
                .existsByStudentIdAndTypeAndStatusIn(student.getId(), type, OPEN_STATUSES);
        if (alreadyOpen) {
            return;
        }

        var alert = new Alert();
        alert.setStudent(student);
        alert.setType(type);
        alert.setSeverity(severity);
        alert.setStatus(AlertStatus.OPEN);
        alert.setDescription(description);
        alert.setMetricValue(metricValue);
        alert.setGeneratedDate(LocalDate.now());
        alert.setPeriod(period);
        target.add(alert);
    }

    /** Gravedad para bajo rendimiento según qué tan lejos está de la nota mínima. */
    private AlertSeverity performanceSeverity(double average) {
        if (average < passingGrade - 1.0) {
            return AlertSeverity.HIGH;
        }
        if (average < passingGrade - 0.5) {
            return AlertSeverity.MEDIUM;
        }
        return AlertSeverity.LOW;
    }

    /** Gravedad según el número de materias perdidas. */
    private AlertSeverity subjectsSeverity(long subjectsAtRisk) {
        if (subjectsAtRisk >= minSubjectsAtRisk + 2) {
            return AlertSeverity.HIGH;
        }
        if (subjectsAtRisk >= minSubjectsAtRisk + 1) {
            return AlertSeverity.MEDIUM;
        }
        return AlertSeverity.LOW;
    }

    /** Gravedad según qué tan bajo está el porcentaje de asistencia. */
    private AlertSeverity attendanceSeverity(double attendancePercent) {
        if (attendancePercent < minAttendancePercent - 20) {
            return AlertSeverity.HIGH;
        }
        if (attendancePercent < minAttendancePercent - 10) {
            return AlertSeverity.MEDIUM;
        }
        return AlertSeverity.LOW;
    }

    /** Toma el primer periodo no nulo encontrado entre las notas. */
    private String resolvePeriod(List<Note> notes) {
        return notes.stream()
                .map(Note::getPeriod)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    /** Redondea a dos decimales para almacenar la métrica de forma legible. */
    private Double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
