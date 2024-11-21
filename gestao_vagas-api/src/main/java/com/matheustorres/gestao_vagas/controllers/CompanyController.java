package com.matheustorres.gestao_vagas.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.gestao_vagas.dtos.CompanyRequestDTO;
import com.matheustorres.gestao_vagas.exceptions.UserFoundException;
import com.matheustorres.gestao_vagas.models.CompanyModel;
import com.matheustorres.gestao_vagas.services.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Object> createCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO) {
        try {
            companyService.findByUsernameOrEmail(companyRequestDTO.username(), companyRequestDTO.email())
                    .ifPresent(user -> {
                        throw new UserFoundException();
                    });

            var companyModel = new CompanyModel();
            BeanUtils.copyProperties(companyRequestDTO, companyModel);
            companyService.save(companyModel);

            return ResponseEntity.ok().body(companyModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
