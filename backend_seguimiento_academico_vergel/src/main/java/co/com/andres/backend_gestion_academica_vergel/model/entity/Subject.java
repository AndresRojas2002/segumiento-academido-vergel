package co.com.andres.backend_gestion_academica_vergel.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
 * Entidad que representa una materia dentro del sistema académico.
 *
 * Cada materia está asociada a un profesor responsable y a un grado,
 * y puede tener múltiples notas registradas por los estudiantes matriculados.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subject {

    /** Identificador único de la materia, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Lista de notas asociadas a esta materia. */
    @OneToMany(mappedBy = "subject")
    private List<Note> notes;

    /** Profesor responsable de dictar la materia. */
    @ManyToOne
    @JoinColumn(name = "professorId")
    private Professors professors;

    /** Grado al que pertenece la materia. */
    @ManyToOne
    @JoinColumn(name = "gradeId")
    private Grade grade;

    /** Nombre de la materia. */
    @Column(nullable = false)
    private String nameSubject;

    /** Descripción del contenido o propósito de la materia. */
    @Column(nullable = false)
    private String description;

    /**
     * Constructor que inicializa la materia únicamente con su ID.
     * Útil para referencias y relaciones sin cargar la entidad completa.
     *
     * @param id Identificador único de la materia
     */
    public Subject(Long id) {
        this.id = id;
    }
}