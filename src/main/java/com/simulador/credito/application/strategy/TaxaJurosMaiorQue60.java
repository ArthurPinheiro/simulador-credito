package com.simulador.credito.application.strategy;

import org.springframework.stereotype.Component;

@Component("taxajurosMaiorQue60")
public class TaxaJurosMaiorQue60 implements TaxaJurosStrategy{
    @Override
    public double calcularTaxaJuros() {
        return 0.04;
    }
}
