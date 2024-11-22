package com.matheustorres.gestao_vagas.controllers;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.matheustorres.gestao_vagas.dtos.AuthCandidateRequestDTO;
import com.matheustorres.gestao_vagas.dtos.AuthCandidateResponseDTO;
import com.matheustorres.gestao_vagas.services.CandidateService;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            var candidate = candidateService.findByUsername(authCandidateRequestDTO.username()).orElseThrow(() -> {
                throw new UsernameNotFoundException("Username/password incorrect");
            });

            var passwordMatches = passwordEncoder
                    .matches(authCandidateRequestDTO.password(), candidate.getPassword());

            if (!passwordMatches) {
                throw new AuthenticationException();
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            var expiresIn = Instant.now().plus(Duration.ofMinutes(10));

            var token = JWT.create()
                    .withIssuer("javagas")
                    .withSubject(candidate.getId().toString())
                    .withClaim("roles", Arrays.asList("CANDIDATE"))
                    .withExpiresAt(expiresIn)
                    .sign(algorithm);

            var AuthCandidateResponse = new AuthCandidateResponseDTO();
            AuthCandidateResponse.setAccess_token(token);
            AuthCandidateResponse.setExpires_in(expiresIn.toEpochMilli());

            return ResponseEntity.ok().body(AuthCandidateResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
