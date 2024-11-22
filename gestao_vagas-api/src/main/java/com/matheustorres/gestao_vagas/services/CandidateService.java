package com.matheustorres.gestao_vagas.services;

import java.util.Optional;
import java.util.UUID;

import com.matheustorres.gestao_vagas.models.CandidateModel;

public interface CandidateService {

    Optional<CandidateModel> findByUsernameOrEmail(String username, String email);

    void save(CandidateModel candidateModel);

    Optional<CandidateModel> findByUsername(String username);

    Optional<CandidateModel> findById(UUID idCandidate);

}
