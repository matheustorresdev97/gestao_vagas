package com.matheustorres.gestao_vagas.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheustorres.gestao_vagas.models.CandidateModel;

public interface CandidateRepository extends JpaRepository<CandidateModel, UUID> {
    Optional<CandidateModel> findByUsernameOrEmail(String username, String email);

    Optional<CandidateModel> findByUsername(String username);
}
