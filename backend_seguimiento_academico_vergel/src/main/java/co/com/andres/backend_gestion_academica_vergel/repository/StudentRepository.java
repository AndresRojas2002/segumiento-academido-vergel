package co.com.andres.backend_gestion_academica_vergel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.backend_gestion_academica_vergel.model.entity.Students;

public interface StudentRepository extends JpaRepository<Students, Long> {

   

    List<Students> findByEmailContainingIgnoreCase(String email);

  

    
    /**
     * Busca un estudiante por su email único.
     * 
     * @param email Email del estudiante a buscar
     * @return Optional que contiene el estudiante si existe, vacío en caso contrario
     */
    Optional<Students> findByEmail(String email);

    /**
     * Busca estudiantes cuyo nombre o apellido contenga el texto especificado (ignorando mayúsculas/minúsculas).
     * 
     * @param text Texto a buscar en el nombre del estudiante
     * @param text2 Texto a buscar en el apellido del estudiante
     * @return Lista de estudiantes que coinciden con el criterio de búsqueda
     */
    List<Students> findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String text, String text2);


    // StudentsRepository
Optional<Students> findByUserName(String userName);


}
