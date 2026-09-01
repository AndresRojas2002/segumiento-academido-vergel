package co.com.andres.backend_gestion_academica_vergel.model.Dto;

/**
 * DTO de respuesta para la autenticación exitosa.
 *
 * Contiene el token JWT generado y el rol del usuario autenticado,
 * para que el cliente pueda tomar decisiones de navegación sin
 * necesidad de decodificar el token.
 *
 * @param token  token JWT generado para el usuario autenticado
 * @param role rol principal del usuario (ROLE_STUDENT, ROLE_PROFESSOR, ROLE_PARENT)
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public record AuthenticateResponse(
        String token,
        String role
) {}