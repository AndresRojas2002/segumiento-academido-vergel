package co.com.andres.backend_gestion_academica_vergel.service;

import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.NoteRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.NoteResponse;

public interface NoteService {
    NoteResponse create(NoteRequest request);

    NoteResponse findById(Long id);

    List<NoteResponse> findAll();

    NoteResponse update(Long id, NoteRequest request);

    void delete(Long id);

}
