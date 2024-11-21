package com.matheustorres.gestao_vagas.services;

import java.util.Optional;

import com.matheustorres.gestao_vagas.models.CompanyModel;

public interface CompanyService {

    Optional<CompanyModel> findByUsernameOrEmail(String username, String email);

    void save(CompanyModel companyModel);

    Optional<CompanyModel> findByUsername(String username);
    
}
