package co.com.andres.backend_gestion_academica_vergel.model.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a un padre o acudiente dentro del sistema académico.
 * 
 * Contiene la información personal del padre o acudiente, su relación con
 * los estudiantes a cargo y los roles asignados en la plataforma.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Parent {

    /** Identificador único del padre, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de usuario único para autenticación en el sistema. */
    @Column(unique = true, nullable = false)
    private String userName;

    /** Contraseña del padre o acudiente (almacenada encriptada). */
    @Column(nullable = false)
    private String password;

    /** Lista de estudiantes a cargo de este padre o acudiente. */
    @OneToMany(mappedBy = "parent")
    private List<Students> students;

    /** Nombre del padre o acudiente. */
    @Column(nullable = false)
    private String name;

    /** Apellido del padre o acudiente. */
    @Column(nullable = false)
    private String lastName;

    /** Correo electrónico único del padre o acudiente. */
    @Column(unique = true, nullable = false)
    private String email;

    /** Dirección de residencia del padre o acudiente. */
    @Column(nullable = false)
    private String address;

    /** Número de teléfono del padre o acudiente. */
    @Column(nullable = false)
    private String phone;

    /** Número de identificación único del padre dentro de la institución. */
    @Column(unique = true, nullable = false)
    private String parentNumber;

    /**
     * Roles asignados al padre o acudiente en el sistema.
     * Colección de roles que define los permisos y accesos
     * que tiene el padre en la plataforma.
     * Se carga de forma eager para optimizar consultas de autenticación y autorización.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "parent_roles",
        joinColumns = @JoinColumn(name = "parent_id"))
    @Column(name = "rol")
    private Set<String> roles;
}