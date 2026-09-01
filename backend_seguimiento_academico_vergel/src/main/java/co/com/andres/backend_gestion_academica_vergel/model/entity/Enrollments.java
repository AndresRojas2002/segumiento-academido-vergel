package co.com.andres.backend_gestion_academica_vergel.model.entity;

import java.time.LocalDate;
import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.shared.EnrollmentsState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una matrícula dentro del sistema académico.
 * 
 * Vincula a un estudiante con un grado específico, registra la fecha
 * de matrícula, su estado actual y las notas asociadas.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Enrollments {

    /** Identificador único de la matrícula, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Lista de notas asociadas a esta matrícula. */
    @OneToMany(mappedBy = "enrollments")
    private List<Note> notes;

    /** Estudiante al que pertenece esta matrícula. */
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Students students;

    /** Grado al que corresponde esta matrícula. */
    @ManyToOne
    @JoinColumn(name = "gradeId")
    private Grade grade;

    /** Fecha en la que se realizó la matrícula. */
    @Column(nullable = false)
    private LocalDate enrollmentDate;

    /** Estado actual de la matrícula (ACTIVE, RETIRED). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentsState enrollmentsState;
}