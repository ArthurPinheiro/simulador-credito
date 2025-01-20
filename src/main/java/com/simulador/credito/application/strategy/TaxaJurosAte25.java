package com.simulador.credito.application.strategy;

public class TaxaJurosAte25 implements TaxaJurosStrategy {
    @Override
    public double calcularTaxaJuros() {
        return 0.05;
    }
}
