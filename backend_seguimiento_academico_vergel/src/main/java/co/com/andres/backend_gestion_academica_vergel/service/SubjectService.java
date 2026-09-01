package co.com.andres.backend_gestion_academica_vergel.service;

import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.SubjectRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.SubjectResponse;

public interface SubjectService {
    SubjectResponse createSubject(SubjectRequest subjectRequest);

    List<SubjectResponse> getAllSubject();

    SubjectResponse updateSubject(Long id, SubjectRequest subjectRequest);

    void deleteSubject(Long id);

    SubjectResponse getByIdSubject(Long id);
}
