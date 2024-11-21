package com.matheustorres.gestao_vagas.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.matheustorres.gestao_vagas.models.CompanyModel;
import com.matheustorres.gestao_vagas.repositories.CompanyRepository;
import com.matheustorres.gestao_vagas.services.CompanyService;

public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Optional<CompanyModel> findByUsernameOrEmail(String username, String email) {
        return companyRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public void save(CompanyModel companyModel) {
        companyRepository.save(companyModel);
    }

}
