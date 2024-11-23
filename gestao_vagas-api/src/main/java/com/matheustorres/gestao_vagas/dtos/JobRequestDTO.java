package com.matheustorres.gestao_vagas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record JobRequestDTO(
        @Schema(example = "Vaga para pessoa desenvolvedora júnior", requiredMode = RequiredMode.REQUIRED) String description,
        @Schema(example = "GymPass, Plano de saúde", requiredMode = RequiredMode.REQUIRED) String benefits,
        @Schema(example = "JUNIOR", requiredMode = RequiredMode.REQUIRED) String level) {

}
