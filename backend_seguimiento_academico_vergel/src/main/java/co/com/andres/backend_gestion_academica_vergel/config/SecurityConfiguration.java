package co.com.andres.backend_gestion_academica_vergel.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de la aplicación.
 *
 * <p>Define los beans necesarios para la autenticación JWT, el proveedor de autenticación,
 * el codificador de contraseñas y las reglas de acceso a los endpoints.</p>
 *
 * @since 2026
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] PUBLIC_URLS = {
        "/api/auth/**",
        "/api/professors",
        "/api/students",
        "/api/parents",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/api-docs/**",
        "/v3/api-docs/**",
        // Recursos estáticos y rutas del frontend Angular (interfaz web integrada)
        "/",
        "/index.html",
        "/favicon.ico",
        "/*.js",
        "/*.css",
        "/assets/**",
        "/media/**",
        // Rutas de navegación de la SPA (reenviadas por SpaController)
        "/login",
        "/alertas",
        "/profesores",
        "/estudiantes",
        "/acudientes",
        "/grados",
        "/materias",
        "/matriculas",
        "/notas",
        "/asistencias"
};

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     *
     * <p>Deshabilita CSRF, establece sesiones sin estado, aplica el filtro JWT
     * y define los endpoints públicos y protegidos.</p>
     *
     * @param http                   objeto de configuración de seguridad HTTP
     * @param authenticationProvider proveedor de autenticación basado en base de datos
     * @return la cadena de filtros de seguridad configurada
     * @throws Exception si ocurre un error durante la configuración
     * @since 2026
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Crea el proveedor de autenticación basado en {@link UserDetailsService}.
     *
     * <p>Utiliza {@link DaoAuthenticationProvider} con el servicio de detalles de usuario
     * y el codificador de contraseñas BCrypt.</p>
     *
     * @param userDetailsService servicio que carga los datos del usuario desde la base de datos
     * @return el proveedor de autenticación configurado
     * @since 2026
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Expone el {@link AuthenticationManager} como bean de Spring.
     *
     * <p>Necesario para que {@code AuthController} pueda inyectarlo y realizar
     * la autenticación de credenciales.</p>
     *
     * @param config configuración de autenticación de Spring Security
     * @return el gestor de autenticación
     * @throws Exception si ocurre un error al obtener el gestor
     * @since 2026
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Crea el codificador de contraseñas BCrypt.
     *
     * <p>Utilizado para encriptar contraseñas al registrar usuarios
     * y para verificarlas durante la autenticación.</p>
     *
     * @return el codificador de contraseñas BCrypt
     * @since 2026
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}