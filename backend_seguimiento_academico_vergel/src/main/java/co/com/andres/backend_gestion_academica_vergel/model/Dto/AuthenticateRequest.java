package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * DTO de solicitud para la autenticación de usuarios.
 *
 * Contiene las credenciales necesarias para generar un token JWT.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */


public record AuthenticateRequest(
    @NotBlank(message = "el nombre de usuario no puede ser nulo")
    @JsonProperty("userName")
    String userName,

    @NotBlank(message = "la contraseña no puede ser nula")
    @Size(min = 8, message = "la contraseña debe tener al menos 8 caracteres")
    @JsonProperty("password")
    String password
) {}