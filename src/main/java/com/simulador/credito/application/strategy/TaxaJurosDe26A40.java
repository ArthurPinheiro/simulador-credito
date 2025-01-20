package com.simulador.credito.application.strategy;

public class TaxaJurosDe26A40 implements TaxaJurosStrategy{
    @Override
    public double calcularTaxaJuros() {
        return 0.03;
    }
}
