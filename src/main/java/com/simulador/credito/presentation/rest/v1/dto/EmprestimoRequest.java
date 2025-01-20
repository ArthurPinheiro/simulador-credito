package com.simulador.credito.presentation.rest.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmprestimoRequest(
        @Positive
        @Schema(description = "Valor do empréstimo", example = "50000")
        BigDecimal valorEmprestimo,
        @Schema(description = "Data de nascimento", example = "2000-01-19")
        LocalDate dataNascimento,
        @Schema(description = "Prazo do pagamento (em meses)", example = "60")
        @Positive int prazoPagamento
) {
}
