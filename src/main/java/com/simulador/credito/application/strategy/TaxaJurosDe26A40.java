package com.simulador.credito.application.strategy;

import org.springframework.stereotype.Component;

@Component("taxajurosDe26A40")
public class TaxaJurosDe26A40 implements TaxaJurosStrategy{
    @Override
    public double calcularTaxaJuros() {
        return 0.03;
    }
}
