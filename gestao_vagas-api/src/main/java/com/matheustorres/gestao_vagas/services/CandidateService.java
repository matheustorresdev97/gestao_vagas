package com.matheustorres.gestao_vagas.services;

import com.matheustorres.gestao_vagas.models.CandidateModel;

import jakarta.validation.Valid;

public interface CandidateService {

    CandidateModel save(CandidateModel candidateModel);
    
}
