package com.matheustorres.gestao_vagas.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheustorres.gestao_vagas.models.CompanyModel;

public interface CompanyRepository extends JpaRepository<CompanyModel, UUID> {
    Optional<CompanyModel> findByUsernameOrEmail(String username, String email);
}
