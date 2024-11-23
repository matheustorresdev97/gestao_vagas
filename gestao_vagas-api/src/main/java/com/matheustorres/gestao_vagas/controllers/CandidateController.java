package com.matheustorres.gestao_vagas.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.gestao_vagas.dtos.CandidateRequestDTO;
import com.matheustorres.gestao_vagas.dtos.ProfileCandidateResponseDTO;
import com.matheustorres.gestao_vagas.exceptions.UserFoundException;
import com.matheustorres.gestao_vagas.models.CandidateModel;
import com.matheustorres.gestao_vagas.models.JobModel;
import com.matheustorres.gestao_vagas.services.CandidateService;
import com.matheustorres.gestao_vagas.services.JobService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JobService jobService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar um candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
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
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
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

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis, baseada no filtro")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobModel.class)))
            })
    })
    public List<JobModel> findJobByFilter(@RequestParam String filter) {
        return jobService.listAllJobsByFilterUseCase(filter);
    }
}
