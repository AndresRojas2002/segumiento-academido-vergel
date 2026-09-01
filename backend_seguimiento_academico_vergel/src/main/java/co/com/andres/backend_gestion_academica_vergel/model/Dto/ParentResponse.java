package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import co.com.andres.backend_gestion_academica_vergel.model.shared.Role;

/**
 * DTO de respuesta con la información de un padre o acudiente.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param id           Identificador único del padre o acudiente
 * @param name         Nombre del padre o acudiente
 * @param lastName     Apellido del padre o acudiente
 * @param phone        Número de teléfono
 * @param address      Dirección de residencia
 * @param email        Correo electrónico
 * @param parentNumber Número de identificación del padre o acudiente
 * @param role         Rol asignado en el sistema
 */
public record ParentResponse(
        Long id,
        String name,
        String lastName,
        String phone,
        String address,
        String email,
        String parentNumber,
        Role role) {
}