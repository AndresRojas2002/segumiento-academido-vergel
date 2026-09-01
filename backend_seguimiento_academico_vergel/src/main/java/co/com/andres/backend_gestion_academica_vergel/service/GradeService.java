package co.com.andres.backend_gestion_academica_vergel.service;


import java.util.List;


import co.com.andres.backend_gestion_academica_vergel.model.Dto.GradeRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.GradeResponse;


public interface GradeService {
    
    GradeResponse createGrade(GradeRequest request);

    GradeResponse findByIdGrade(Long id);

    List<GradeResponse> findAllGrade();

    GradeResponse updateGrade(Long id, GradeRequest request);

    void deleteGrade(Long id);

    

   
}
