package com.matheustorres.gestao_vagas.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.gestao_vagas.models.CandidateModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @PostMapping
    public void create(@Valid @RequestBody CandidateModel candidateModel) {
        System.out.println("Candidato");
        System.out.println(candidateModel.getEmail());
    }
}
