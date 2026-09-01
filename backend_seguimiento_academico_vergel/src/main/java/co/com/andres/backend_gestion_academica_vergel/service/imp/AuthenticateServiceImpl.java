package co.com.andres.backend_gestion_academica_vergel.service.imp;


import co.com.andres.backend_gestion_academica_vergel.config.exception.authenticate.InvalidCredentialsException;
import co.com.andres.backend_gestion_academica_vergel.repository.ParentRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.ProfessorRepository;
import co.com.andres.backend_gestion_academica_vergel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Implementación de {@link UserDetailsService} para la autenticación JWT.
 *
 * Busca el usuario por {@code userName} en las tres tablas: estudiantes,
 * profesores y padres. El primero que coincida se usa para la autenticación.
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final StudentRepository studentsRepository;
    private final ProfessorRepository professorRepository;
    private final ParentRepository parentRepository;

    /**
     * Carga los detalles del usuario buscando por {@code userName}
     * en estudiantes, profesores y padres, en ese orden.
     *
     * @param username nombre de usuario a buscar
     * @return detalles del usuario para Spring Security
     * @throws UsernameNotFoundException si el usuario no existe en ninguna tabla
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var student = studentsRepository.findByUserName(username);
        if (student.isPresent()) {
            var s = student.get();
            return User.builder()
                    .username(s.getUserName())
                    .password(s.getPassword())
                    .authorities(s.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                            .collect(Collectors.toList()))
                    .build();
        }

        var professor = professorRepository.findByUserName(username);
        if (professor.isPresent()) {
            var p = professor.get();
            return User.builder()
                    .username(p.getUserName())
                    .password(p.getPassword())
                    .authorities(p.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                            .collect(Collectors.toList()))
                    .build();
        }

        var parent = parentRepository.findByUserName(username);
        if (parent.isPresent()) {
            var pa = parent.get();
            return User.builder()
                    .username(pa.getUserName())
                    .password(pa.getPassword())
                    .authorities(pa.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                            .collect(Collectors.toList()))
                    .build();
        }

        throw new InvalidCredentialsException();
    }
}