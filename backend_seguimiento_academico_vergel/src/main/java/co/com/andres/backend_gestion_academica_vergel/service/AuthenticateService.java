package co.com.andres.backend_gestion_academica_vergel.service;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Contrato para el servicio de autenticación de usuarios.
 *
 * Define la operación de carga de usuarios por nombre de usuario
 * para ser utilizada por Spring Security en el proceso de autenticación JWT.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
public interface AuthenticateService {

    /**
     * Carga los detalles del usuario por su nombre de usuario.
     *
     * @param username nombre de usuario a buscar
     * @return detalles del usuario para Spring Security
     * @throws UsernameNotFoundException si el usuario no existe
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
