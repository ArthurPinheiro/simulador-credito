package com.simulador.credito.application.strategy;

import org.springframework.stereotype.Component;

@Component("taxajurosAte25")
public class TaxaJurosAte25 implements TaxaJurosStrategy {
    @Override
    public double calcularTaxaJuros() {
        return 0.05;
    }
}
