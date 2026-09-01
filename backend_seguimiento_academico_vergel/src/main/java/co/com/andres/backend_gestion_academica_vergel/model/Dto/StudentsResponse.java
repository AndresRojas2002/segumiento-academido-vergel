package co.com.andres.backend_gestion_academica_vergel.model.Dto;

import co.com.andres.backend_gestion_academica_vergel.model.shared.Role;

/**
 * DTO de respuesta con la información de un estudiante.
 * 
 * @author Andres
 * @version 1.0
 * @since 2026
 * 
 * @param id            Identificador único del estudiante
 * @param name          Nombre del estudiante
 * @param lastName      Apellido del estudiante
 * @param phone         Número de teléfono
 * @param address       Dirección de residencia
 * @param email         Correo electrónico
 * @param studentNumber Número de identificación del estudiante
 * @param nameParent    Nombre del padre o acudiente responsable
 * @param role          Rol asignado en el sistema
 */
public record StudentsResponse(
        Long id,
        String name,
        String lastName,
        String phone,
        String address,
        String email,
        String studentNumber,
        String nameParent,
        Role role) {

}