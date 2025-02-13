package com.simulador.credito.application.factory;

import org.springframework.stereotype.Component;

import com.simulador.credito.application.strategy.TaxaJurosEnum;
import com.simulador.credito.application.strategy.TaxaJurosStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaxaJurosFactory {

    private final TaxaJurosStrategy strategy;

    public TaxaJurosStrategy obterTaxaJuros(int faixaEtaria) {
        TaxaJurosEnum typeJuros = TaxaJurosEnum.fromTaxaJuros(faixaEtaria);

        try {
            return typeJuros.getStrategy()
            .getConstructor(TaxaJurosStrategy.class)
            .newInstance(strategy);
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }
}
