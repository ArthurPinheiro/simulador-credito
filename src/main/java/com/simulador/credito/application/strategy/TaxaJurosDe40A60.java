package com.simulador.credito.application.strategy;

public class TaxaJurosDe40A60 implements TaxaJurosStrategy{
    @Override
    public double calcularTaxaJuros() {
        return 0.02;
    }
}
