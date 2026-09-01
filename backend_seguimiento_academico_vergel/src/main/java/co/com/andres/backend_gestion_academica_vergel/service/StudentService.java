package co.com.andres.backend_gestion_academica_vergel.service;

import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.StudentsRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.StudentsResponse;

public interface StudentService {

    List<StudentsResponse> getAllStudents();

    StudentsResponse getById(Long id);

    StudentsResponse createStudents(StudentsRequest studentsRequest);

    void deleteById(Long id);

    StudentsResponse updateStudents(Long id, StudentsRequest studentsRequest);

    List<StudentsResponse> getByNameOrLastName(String text);

    List<StudentsResponse> getByEmail(String email);

}
