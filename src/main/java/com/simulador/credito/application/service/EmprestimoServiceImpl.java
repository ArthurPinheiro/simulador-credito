package com.simulador.credito.application.service;

import com.simulador.credito.application.factory.TaxaJurosFactory;
import com.simulador.credito.application.strategy.TaxaJurosStrategy;
import com.simulador.credito.domain.model.Email;
import com.simulador.credito.infra.adapter.EmailAdapter;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoRequest;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    private final EmailAdapter emailAdapter;

    public EmprestimoServiceImpl(EmailAdapter emailAdapter) {
        this.emailAdapter = emailAdapter;
    }

    @Override
    public EmprestimoResponse simularCredito(EmprestimoRequest request) {
        int faixaEtaria = calcularIdade(request.dataNascimento());
        double taxaJuros = taxaJuros(faixaEtaria);
        BigDecimal parcelaMensal = calcularParcelaMensal(request.valorEmprestimo(), taxaJuros, request.prazoPagamento());
        BigDecimal valorTotal = parcelaMensal.multiply(BigDecimal.valueOf(request.prazoPagamento()));
        BigDecimal valorTotalJuros = valorTotal.subtract(request.valorEmprestimo());
        var response = new EmprestimoResponse(valorTotal, parcelaMensal, valorTotalJuros);
        enviarEmail(response);
        return response;
    }

    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    private double taxaJuros(int faixaEtaria) {
        TaxaJurosStrategy strategy = TaxaJurosFactory.obterTaxaJuros(faixaEtaria);
        return strategy.calcularTaxaJuros();
    }

    private BigDecimal calcularParcelaMensal(BigDecimal valorEmprestimo, double taxaJurosAnual, int prazoMeses) {
        BigDecimal taxaMensal = BigDecimal.valueOf(taxaJurosAnual / 12);
        BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaMensal);
        BigDecimal potencia = BigDecimal.ONE.divide(umMaisTaxa.pow(prazoMeses), 10, RoundingMode.HALF_UP);
        BigDecimal divisor = BigDecimal.ONE.subtract(potencia);

        return valorEmprestimo.multiply(taxaMensal).divide(divisor, RoundingMode.HALF_UP);
    }

    private void enviarEmail(EmprestimoResponse response) {
        String body = """
                Valor Total do empréstimo: %s
                Valor Total dos juros: %s
                Valor da parcela mensal: %f
                """.formatted(
                        response.valorTotal(),
                response.juros(), response.valorMensal());

        var email = new Email(
                "teste@email.com",
                "Simulador de crédito",
                body
        );

        emailAdapter.enviar(email);
    }

}
