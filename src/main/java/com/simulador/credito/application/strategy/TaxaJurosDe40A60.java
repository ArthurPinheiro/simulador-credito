package com.simulador.credito.application.strategy;

import org.springframework.stereotype.Component;

@Component("taxajurosDe40A60")
public class TaxaJurosDe40A60 implements TaxaJurosStrategy{
    @Override
    public double calcularTaxaJuros() {
        return 0.02;
    }
}
