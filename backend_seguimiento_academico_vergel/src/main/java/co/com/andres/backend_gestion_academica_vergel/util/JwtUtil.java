package co.com.andres.backend_gestion_academica_vergel.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * Utilidad para la generación y validación de tokens JWT.
 *
 * Proporciona métodos para crear tokens, extraer claims y verificar
 * la validez del token contra un {@link UserDetails}.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Genera un token JWT para el usuario autenticado.
     *
     * @param userDetails detalles del usuario autenticado
     * @return token JWT firmado
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * Extrae el nombre de usuario del token JWT.
     *
     * @param token token JWT
     * @return nombre de usuario contenido en el subject del token
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Valida el token JWT contra los detalles del usuario.
     *
     * @param token       token JWT a validar
     * @param userDetails detalles del usuario a comparar
     * @return {@code true} si el token es válido y no ha expirado
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Extrae un claim específico del token JWT.
     *
     * @param token          token JWT
     * @param claimsResolver función que extrae el claim deseado
     * @param <T>            tipo del claim
     * @return valor del claim extraído
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token JWT.
     *
     * @param token token JWT
     * @return todos los claims contenidos en el token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Verifica si el token JWT ha expirado.
     *
     * @param token token JWT
     * @return {@code true} si el token ya expiró
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Construye la clave secreta para firmar y verificar tokens.
     *
     * @return clave secreta HMAC-SHA
     */
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}