package com.matheustorres.gestao_vagas.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheustorres.gestao_vagas.models.CandidateModel;
import com.matheustorres.gestao_vagas.repositories.CandidateRepository;
import com.matheustorres.gestao_vagas.services.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Optional<CandidateModel> findByUsernameOrEmail(String username, String email) {
        return candidateRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public void save(CandidateModel candidateModel) {
        candidateRepository.save(candidateModel);
    }

    @Override
    public Optional<CandidateModel> findByUsername(String username) {
        return candidateRepository.findByUsername(username);
    }

    @Override
    public Optional<CandidateModel> findById(UUID idCandidate) {
        return candidateRepository.findById(idCandidate);
    }
}
