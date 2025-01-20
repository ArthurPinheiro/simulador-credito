package com.simulador.credito.presentation.rest.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record EmprestimoResponse(
        @Schema(description = "Valor total do empréstimo", example = "58000")
        BigDecimal valorTotal,
        @Schema(description = "Valor mensal empréstimo", example = "550")
        BigDecimal valorMensal,
        @Schema(description = "Juros do empréstimo", example = "8000")
        BigDecimal juros ) {

    public EmprestimoResponse {
        valorTotal = valorTotal.setScale(2, RoundingMode.HALF_UP);
        valorMensal = valorMensal.setScale(2, RoundingMode.HALF_UP);
        juros = juros.setScale(2, RoundingMode.HALF_UP);
    }
}
