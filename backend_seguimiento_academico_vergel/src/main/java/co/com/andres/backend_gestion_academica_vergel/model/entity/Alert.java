package co.com.andres.backend_gestion_academica_vergel.model.entity;

import java.time.LocalDate;
import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertSeverity;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertStatus;
import co.com.andres.backend_gestion_academica_vergel.model.shared.AlertType;
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
 * Entidad que representa una alerta temprana generada por el sistema.
 *
 * Una alerta describe una situación de riesgo detectada para un
 * estudiante (bajo rendimiento, varias materias perdidas o inasistencia),
 * guarda el valor de la métrica que la disparó y su ciclo de vida.
 * Sobre cada alerta el docente puede registrar acciones de seguimiento.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Alert {

    /** Identificador único de la alerta, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Estudiante al que corresponde la alerta. */
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Students student;

    /** Tipo de situación de riesgo detectada. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType type;

    /** Nivel de gravedad de la alerta. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertSeverity severity;

    /** Estado actual de la alerta (OPEN, IN_PROGRESS, RESOLVED). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertStatus status;

    /** Mensaje descriptivo generado automáticamente. */
    @Column(nullable = false, length = 500)
    private String description;

    /** Valor de la métrica que disparó la alerta (promedio, %, conteo). */
    @Column
    private Double metricValue;

    /** Fecha en la que se generó la alerta. */
    @Column(nullable = false)
    private LocalDate generatedDate;

    /** Periodo académico evaluado (por ejemplo "2026-1"). */
    @Column
    private String period;

    /** Acciones de seguimiento registradas por el docente sobre esta alerta. */
    @OneToMany(mappedBy = "alert")
    private List<Followup> followups;
}
