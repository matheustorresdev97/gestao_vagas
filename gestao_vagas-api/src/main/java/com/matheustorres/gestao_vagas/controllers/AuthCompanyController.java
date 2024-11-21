package com.matheustorres.gestao_vagas.controllers;

import java.time.Duration;
import java.time.Instant;

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
import com.matheustorres.gestao_vagas.dtos.AuthCompanyRequestDTO;
import com.matheustorres.gestao_vagas.services.CompanyService;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret}")
    private String secretKey;

    @PostMapping("/company")
    public ResponseEntity<Object> createCompanyAuth(@RequestBody AuthCompanyRequestDTO authCompanyRequestDTO) {
        try {
            var company = companyService.findByUsername(authCompanyRequestDTO.username()).orElseThrow(() -> {
                throw new UsernameNotFoundException("Username/password incorrect");
            });

            var passwordMatches = this.passwordEncoder.matches(authCompanyRequestDTO.password(), company.getPassword());

            if (!passwordMatches) {
                throw new AuthenticationException();
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                    .withSubject(company.getId().toString()).sign(algorithm);

            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
