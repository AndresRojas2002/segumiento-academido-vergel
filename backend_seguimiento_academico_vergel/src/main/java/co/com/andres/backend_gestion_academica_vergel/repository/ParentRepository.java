package co.com.andres.backend_gestion_academica_vergel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.backend_gestion_academica_vergel.model.entity.Parent;

public interface ParentRepository  extends JpaRepository<Parent,Long>{

    List<Parent> findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String text, String text2);

    List<Parent> findByEmailContainingIgnoreCase(String email);

      /**
     * Busca un estudiante por su email único.
     * 
     * @param email Email del estudiante a buscar
     * @return Optional que contiene el estudiante si existe, vacío en caso contrario
     */
      Optional<Parent> findByEmail(String email);



// ParentRepository
Optional<Parent> findByUserName(String userName);
}
