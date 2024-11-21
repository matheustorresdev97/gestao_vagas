package com.matheustorres.gestao_vagas.services.impl;

import java.util.Optional;

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
}
