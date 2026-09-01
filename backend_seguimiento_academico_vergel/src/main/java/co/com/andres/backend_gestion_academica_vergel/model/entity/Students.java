package co.com.andres.backend_gestion_academica_vergel.model.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
 * Entidad que representa a un estudiante dentro del sistema académico.
 * 
 * Contiene la información personal del estudiante, su relación con el padre
 * o acudiente, sus matrículas activas y los roles asignados en la plataforma.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Students {

    /** Identificador único del estudiante, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de usuario único para autenticación en el sistema. */
    @Column(unique = true, nullable = false)
    private String userName;

    /** Contraseña del estudiante (almacenada encriptada). */
    @Column(nullable = false)
    private String password;

    /** Lista de matrículas asociadas al estudiante. */
    @OneToMany(mappedBy = "students")
    private List<Enrollments> enrollments;

    /** Padre o acudiente responsable del estudiante. */
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Parent parent;

    /** Nombre del estudiante. */
    @Column(nullable = false)
    private String name;

    /** Apellido del estudiante. */
    @Column(nullable = false)
    private String lastName;

    /** Correo electrónico único del estudiante. */
    @Column(unique = true, nullable = false)
    private String email;

    /** Dirección de residencia del estudiante. */
    @Column(nullable = false)
    private String address;

    /** Número de teléfono del estudiante. */
    @Column(nullable = false)
    private String phone;

    /** Número de identificación único del estudiante dentro de la institución. */
    @Column(unique = true, nullable = false)
    private String studentNumber;

    /**
     * Roles asignados al estudiante en el sistema.
     * Colección de roles que define los permisos y accesos
     * que tiene el estudiante en la plataforma.
     * Se carga de forma eager para optimizar consultas de autenticación y
     * autorización.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_roles", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "rol")
    private Set<String> roles;

    /**
     * Constructor que inicializa el estudiante únicamente con su ID.
     * Útil para referencias y relaciones sin cargar la entidad completa.
     * 
     * @param id Identificador único del estudiante
     */
    public Students(Long id) {
        this.id = id;
    }
}