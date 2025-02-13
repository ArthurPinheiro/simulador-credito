package com.simulador.credito.application.strategy;

import java.util.Arrays;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TaxaJurosEnum {
    
    ATE_25(25, TaxaJurosAte25.class),
    ATE_40(40, TaxaJurosDe26A40.class),
    ATE_60(60, TaxaJurosDe40A60.class),
    MAIOR_60(61, TaxaJurosMaiorQue60.class);

    public final int faixaEtaria;
    public final Class<? extends TaxaJurosStrategy> strategy;

    public static TaxaJurosEnum fromTaxaJuros(int faixaEtaria) {
        return Arrays.stream(values())
        .filter(idade -> idade.faixaEtaria >= faixaEtaria)
        .findFirst()
        .orElseThrow(() -> new RuntimeException());
    }
}
