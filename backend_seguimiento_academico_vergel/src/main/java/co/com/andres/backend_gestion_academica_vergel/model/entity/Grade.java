package co.com.andres.backend_gestion_academica_vergel.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un grado o curso dentro del sistema académico.
 * 
 * Cada grado agrupa un conjunto de matrículas y materias,
 * y sirve como unidad organizativa principal de la institución.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Grade {

    /** Identificador único del grado, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Lista de matrículas asociadas a este grado. */
    @OneToMany(mappedBy = "grade")
    private List<Enrollments> enrollments;

    /** Lista de materias que pertenecen a este grado. */
    @OneToMany(mappedBy = "grade")
    private List<Subject> subjects;

    /** Nombre del grado o curso. */
    @Column(nullable = false)
    private String nameGrade;
}
