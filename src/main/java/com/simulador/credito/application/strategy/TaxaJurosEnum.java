package com.simulador.credito.application.strategy;

import java.util.Arrays;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TaxaJurosEnum {
    
    ATE_25(25, "taxajurosAte25"),
    ATE_40(40, "taxajurosDe26A40"),
    ATE_60(60, "taxajurosDe40A60"),
    MAIOR_60(61, "taxajurosMaiorQue60");

    public final int faixaEtaria;
    public final String strategy;

    public static String fromTaxaJuros(int faixaEtaria) {
        return Arrays.stream(values())
        .filter(idade -> idade.faixaEtaria >= faixaEtaria)
        .findFirst()
        .map(TaxaJurosEnum::getStrategy)
        .orElseThrow(() -> new RuntimeException());
    }
}
