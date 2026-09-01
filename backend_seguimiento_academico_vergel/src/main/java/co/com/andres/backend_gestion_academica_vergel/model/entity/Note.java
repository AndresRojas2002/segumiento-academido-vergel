package co.com.andres.backend_gestion_academica_vergel.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una nota académica dentro del sistema.
 * 
 * Cada nota está asociada a una matrícula y a una materia específica,
 * y almacena el valor numérico obtenido por el estudiante.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Note {

    /** Identificador único de la nota, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Matrícula a la que pertenece esta nota. */
    @ManyToOne
    @JoinColumn(name = "enrollmentId")
    private Enrollments enrollments;

    /** Materia a la que corresponde esta nota. */
    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subject;

    /** Valor numérico de la nota obtenida. */
    @Column
    private Double value;

    /** Periodo académico al que corresponde la nota (por ejemplo "2026-1"). */
    @Column
    private String period;
}