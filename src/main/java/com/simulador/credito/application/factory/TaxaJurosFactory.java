package com.simulador.credito.application.factory;

import com.simulador.credito.application.strategy.TaxaJurosAte25;
import com.simulador.credito.application.strategy.TaxaJurosDe26A40;
import com.simulador.credito.application.strategy.TaxaJurosDe40A60;
import com.simulador.credito.application.strategy.TaxaJurosMaiorQue60;
import com.simulador.credito.application.strategy.TaxaJurosStrategy;

public class TaxaJurosFactory {

    public static TaxaJurosStrategy obterTaxaJuros(int faixaEtaria) {
        if (faixaEtaria <= 25) {
            return new TaxaJurosAte25();
        } else if (faixaEtaria <= 40) {
            return new TaxaJurosDe26A40();
        } else if (faixaEtaria <= 60) {
            return new TaxaJurosDe40A60();
        } else {
            return new TaxaJurosMaiorQue60();
        }
    }
}
