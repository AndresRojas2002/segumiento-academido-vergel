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
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a un profesor dentro del sistema académico.
 * 
 * Contiene la información personal del profesor, las materias que dicta
 * y los roles asignados en la plataforma.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professors {

    /** Identificador único del profesor, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de usuario único para autenticación en el sistema. */
    @Column(unique = true, nullable = false)
    private String userName;

    /** Contraseña del profesor (almacenada encriptada). */
    @Column(nullable = false)
    private String password;

    /** Lista de materias que dicta el profesor. */
    @OneToMany(mappedBy = "professors")
    private List<Subject> subjects;

    /** Nombre del profesor. */
    @Column(nullable = false)
    private String name;

    /** Apellido del profesor. */
    @Column(nullable = false)
    private String lastName;

    /** Correo electrónico único del profesor. */
    @Column(unique = true, nullable = false)
    private String email;

    /** Dirección de residencia del profesor. */
    @Column(nullable = false)
    private String address;

    /** Número de teléfono del profesor. */
    @Column(nullable = false)
    private String phone;

    /** Número de identificación único del profesor dentro de la institución. */
    @Column(unique = true, nullable = false)
    private String professorNumber;

    /**
     * Roles asignados al profesor en el sistema.
     * Colección de roles que define los permisos y accesos
     * que tiene el profesor en la plataforma.
     * Se carga de forma eager para optimizar consultas de autenticación y autorización.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "professor_roles",
        joinColumns = @JoinColumn(name = "professor_id"))
    @Column(name = "rol")
    private Set<String> roles;
}