package co.com.andres.backend_gestion_academica_vergel.model.entity;

import java.time.LocalDate;

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
 * Entidad que representa una acción de seguimiento del docente sobre una alerta.
 *
 * Permite registrar qué se hizo frente a la situación detectada (por ejemplo,
 * una citación al acudiente, una tutoría o un compromiso), quién la realizó
 * y su resultado. Es lo que convierte al sistema en una herramienta de
 * acompañamiento y no solo de detección.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Followup {

    /** Identificador único de la acción de seguimiento, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Alerta sobre la que se registra la acción. */
    @ManyToOne
    @JoinColumn(name = "alertId")
    private Alert alert;

    /** Docente que realizó la acción (opcional). */
    @ManyToOne
    @JoinColumn(name = "professorId")
    private Professors professor;

    /** Fecha en la que se realizó la acción. */
    @Column(nullable = false)
    private LocalDate date;

    /** Descripción de la acción realizada. */
    @Column(nullable = false, length = 500)
    private String action;

    /** Resultado o compromiso derivado de la acción (opcional). */
    @Column(length = 500)
    private String result;
}
