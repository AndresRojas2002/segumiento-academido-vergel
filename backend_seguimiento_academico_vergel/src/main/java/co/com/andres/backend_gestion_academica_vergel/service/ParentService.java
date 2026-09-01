package co.com.andres.backend_gestion_academica_vergel.service;

import java.util.List;

import co.com.andres.backend_gestion_academica_vergel.model.Dto.ParentRequest;
import co.com.andres.backend_gestion_academica_vergel.model.Dto.ParentResponse;

public interface ParentService {
    
ParentResponse createParent (ParentRequest request);

ParentResponse findByIdParent (Long id);

List<ParentResponse> findAllParent();

ParentResponse updateParent (Long id, ParentRequest request);

void deleteParent (Long id);


}
