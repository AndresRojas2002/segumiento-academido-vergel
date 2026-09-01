package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear o actualizar un estudiante.
 * 
 * Si no se proporcionan teléfono o dirección, se asigna
 * "no especificado" como valor por defecto.
 * El rol se asigna automáticamente en el servidor como ROLE_STUDENT.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param userName      Nombre de usuario para autenticación
 * @param password      Contraseña del estudiante
 * @param name          Nombre del estudiante
 * @param lastName      Apellido del estudiante
 * @param phone         Número de teléfono (opcional)
 * @param address       Dirección de residencia (opcional)
 * @param email         Correo electrónico en formato válido
 * @param studentNumber Número de identificación del estudiante
 * @param parentId      ID del padre o acudiente responsable
 */
public record StudentsRequest(

        @NotBlank(message = "el nombre de usuario no puede ser nulo")
        @JsonAlias({"usuario", "user_name"})
        String userName,

        @NotBlank(message = "la contraseña no puede ser nula")
        @Size(min = 8, message = "la contraseña debe tener al menos 8 caracteres")
        @JsonAlias({"contraseña", "password"})
        String password,

        @NotBlank(message = "el nombre no puede ser nulo")
        @JsonAlias({"nombre"})
        String name,

        @NotBlank(message = "el apellido no puede ser nulo")
        @JsonAlias({"apellido"})
        String lastName,

        @Size(min = 7, max = 15, message = "el número de teléfono debe tener entre 7 y 15 caracteres")
        @JsonAlias({"numero_telefono", "telefono"})
        String phone,

        @JsonAlias({"direccion"})
        String address,

        @NotBlank(message = "el correo electrónico no puede ser nulo")
        @Email(message = "el formato del correo electrónico no es válido")
        @JsonAlias({"correo_electronico", "email"})
        String email,

        @NotBlank(message = "el numero de identificacion no puede ser nulo")
        @JsonAlias({"numero_identificacion", "cedula", "identificacion"})
        String studentNumber,

        @NotNull(message = "el acudiente no puede ser nulo")
        @JsonAlias({"acudiente", "id_acudiente"})
        Long parentId) {

    public StudentsRequest {
        if (phone == null) {
            phone = "no especificado";
        }
        if (address == null) {
            address = "no especificado";
        }
    }
}