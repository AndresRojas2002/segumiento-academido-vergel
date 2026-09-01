package co.com.andres.backend_gestion_academica_vergel.controller;


import co.com.andres.backend_gestion_academica_vergel.model.Dto.AuthenticateRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.AuthenticateResponse;
import co.com.andres.backend_gestion_academica_vergel.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para la autenticación de usuarios.
 *
 * Expone el endpoint de login que valida las credenciales y retorna
 * un token JWT junto con el rol del usuario autenticado.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Operaciones de autenticación y generación de token JWT")
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final UserDetailsService userDetailsService;
        private final JwtUtil jwtUtil;

    /**
     * Autentica un usuario y retorna un token JWT.
     *
     * @param request credenciales del usuario (userName y password)
     * @return token JWT y rol del usuario autenticado
     */
    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica al usuario con sus credenciales y retorna un token JWT"
    )
    @ApiResponse(responseCode = "200", description = "Autenticación exitosa")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    public ResponseEntity<AuthenticateResponse> login(@Valid @RequestBody AuthenticateRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.userName(), request.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.userName());

        String token = jwtUtil.generateToken(userDetails);

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("UNKNOWN");

        return ResponseEntity.ok(new AuthenticateResponse(token, role));
    }
}
