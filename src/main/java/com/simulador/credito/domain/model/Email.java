package com.simulador.credito.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record Email(
        @Schema(description = "Valor total do empréstimo", example = "creditas@email")
        String to,
        @Schema(description = "Assunto do email", example = "Simulação de empréstimo")
        String subject,
        @Schema(description = "Corpo do email", example = "Corpo do email")
        String body) {}
