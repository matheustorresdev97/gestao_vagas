package com.matheustorres.gestao_vagas.controllers;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.gestao_vagas.dtos.CandidateRequestDTO;
import com.matheustorres.gestao_vagas.dtos.ProfileCandidateResponseDTO;
import com.matheustorres.gestao_vagas.exceptions.UserFoundException;
import com.matheustorres.gestao_vagas.models.CandidateModel;
import com.matheustorres.gestao_vagas.services.CandidateService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateRequestDTO candidateRequestDTO) {
        try {
            candidateService.findByUsernameOrEmail(candidateRequestDTO.username(),
                    candidateRequestDTO.email()).ifPresent(user -> {
                        throw new UserFoundException();
                    });

            var candidateModel = new CandidateModel();
            BeanUtils.copyProperties(candidateRequestDTO, candidateModel);
            var password = passwordEncoder.encode(candidateModel.getPassword());
            candidateModel.setPassword(password);
            candidateService.save(candidateModel);

            return ResponseEntity.ok().body(candidateModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<Object> getCandidate(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var candidate = candidateService.findById(UUID.fromString(idCandidate.toString())).orElseThrow(() -> {
                throw new UsernameNotFoundException("User not found");
            });

            var candidateDTO = ProfileCandidateResponseDTO.builder()
            .description(candidate.getDescription())
            .username(candidate.getUsername())
            .email(candidate.getEmail())
            .name(candidate.getName())
            .id(candidate.getId())
            .build();

            return ResponseEntity.ok().body(candidateDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
