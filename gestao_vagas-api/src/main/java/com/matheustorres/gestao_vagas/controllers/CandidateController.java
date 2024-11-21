package com.matheustorres.gestao_vagas.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.gestao_vagas.dtos.CandidateRequestDTO;
import com.matheustorres.gestao_vagas.exceptions.UserFoundException;
import com.matheustorres.gestao_vagas.models.CandidateModel;
import com.matheustorres.gestao_vagas.services.CandidateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateRequestDTO candidateRequestDTO) {
        try {
            candidateService.findByUsernameOrEmail(candidateRequestDTO.username(),
                    candidateRequestDTO.email()).ifPresent(user -> {
                        throw new UserFoundException();
                    });

            var candidateModel = new CandidateModel();
            BeanUtils.copyProperties(candidateRequestDTO, candidateModel);

            return ResponseEntity.ok().body(candidateService.save(candidateModel));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
