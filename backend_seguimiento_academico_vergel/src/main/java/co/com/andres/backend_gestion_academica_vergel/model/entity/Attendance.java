package co.com.andres.backend_gestion_academica_vergel.model.entity;

import java.time.LocalDate;

import co.com.andres.backend_gestion_academica_vergel.model.shared.AttendanceState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un registro de asistencia dentro del sistema.
 *
 * Cada registro asocia la asistencia de un estudiante (a través de su
 * matrícula) a una materia y una fecha concreta, guardando su estado.
 * Es la fuente de datos para la regla de alerta por inasistencia.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Attendance {

    /** Identificador único del registro de asistencia, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Matrícula (estudiante + grado) a la que pertenece el registro. */
    @ManyToOne
    @JoinColumn(name = "enrollmentId")
    private Enrollments enrollments;

    /** Materia en la que se registra la asistencia. */
    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subject;

    /** Fecha de la sesión de clase registrada. */
    @Column(nullable = false)
    private LocalDate date;

    /** Estado de la asistencia (PRESENT, ABSENT, LATE, EXCUSED). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceState state;

    /** Periodo académico al que corresponde el registro (por ejemplo "2026-1"). */
    @Column
    private String period;
}
