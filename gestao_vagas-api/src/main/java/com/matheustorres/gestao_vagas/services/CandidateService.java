package com.matheustorres.gestao_vagas.services;

import java.util.Optional;

import com.matheustorres.gestao_vagas.models.CandidateModel;



public interface CandidateService {

    Optional<CandidateModel> findByUsernameOrEmail(String username, String email);

    Object save(CandidateModel candidateModel);
    
}
