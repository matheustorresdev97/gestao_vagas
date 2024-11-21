package com.matheustorres.gestao_vagas.dtos;

public record CandidateRequestDTO(String name, String username, String email, String password, String description,
        String curriculum) {

}
