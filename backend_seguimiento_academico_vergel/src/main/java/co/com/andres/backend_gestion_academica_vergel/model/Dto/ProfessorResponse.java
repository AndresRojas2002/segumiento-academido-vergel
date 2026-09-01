package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import co.com.andres.backend_gestion_academica_vergel.model.shared.Role;

/**
 * DTO de respuesta con la información de un profesor.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param id              Identificador único del profesor
 * @param name            Nombre del profesor
 * @param lastName        Apellido del profesor
 * @param phone           Número de teléfono
 * @param address         Dirección de residencia
 * @param email           Correo electrónico
 * @param professorNumber Número de identificación del profesor
 * @param role            Rol asignado en el sistema
 */
public record ProfessorResponse(
        Long id,
        String name,
        String lastName,
        String phone,
        String address,
        String email,
        String professorNumber,
        Role role) {

}