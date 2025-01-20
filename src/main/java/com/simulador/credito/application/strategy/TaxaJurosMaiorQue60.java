package com.simulador.credito.application.strategy;

public class TaxaJurosMaiorQue60 implements TaxaJurosStrategy{
    @Override
    public double calcularTaxaJuros() {
        return 0.04;
    }
}
