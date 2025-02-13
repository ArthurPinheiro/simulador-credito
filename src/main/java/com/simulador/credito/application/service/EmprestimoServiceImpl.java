package com.simulador.credito.application.service;

import com.simulador.credito.application.exception.BusinessException;
import com.simulador.credito.application.factory.EmailFactory;
import com.simulador.credito.application.factory.TaxaJurosFactory;
import com.simulador.credito.application.strategy.TaxaJurosStrategy;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoRequest;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmprestimoServiceImpl implements EmprestimoService {

    public final EmailFactory emailFactory;
    public final TaxaJurosFactory factory;

    @Override
    public EmprestimoResponse simularCredito(EmprestimoRequest request) {
        log.info("Iniciando processo de simulação de crédito");
        int faixaEtaria = calcularIdade(request.dataNascimento());
        double taxaJuros = taxaJuros(faixaEtaria);
        BigDecimal parcelaMensal = calcularParcelaMensal(request.valorEmprestimo(), taxaJuros, request.prazoPagamento());
        BigDecimal valorTotal = parcelaMensal.multiply(BigDecimal.valueOf(request.prazoPagamento()));
        BigDecimal valorTotalJuros = valorTotal.subtract(request.valorEmprestimo());
        var response = new EmprestimoResponse(valorTotal, parcelaMensal, valorTotalJuros);
        emailFactory.enviarEmail(response);
        return response;
    }

    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    private double taxaJuros(int faixaEtaria) {
        TaxaJurosStrategy strategy = factory.obterTaxaJuros(faixaEtaria);
        return strategy.calcularTaxaJuros();
    }

    private BigDecimal calcularParcelaMensal(BigDecimal valorEmprestimo, double taxaJurosAnual, int prazoMeses) {
        try {
            log.info("Calculando parcela de mensalidade");
            valicaoValoresParcelaMensal(valorEmprestimo, taxaJurosAnual, prazoMeses);
            BigDecimal taxaMensal = BigDecimal.valueOf(taxaJurosAnual / 12);
            BigDecimal taxaMensalSomada = BigDecimal.ONE.add(taxaMensal);
            BigDecimal totalPagamento = BigDecimal.ONE.divide(taxaMensalSomada.pow(prazoMeses), 10, RoundingMode.HALF_UP);
            BigDecimal divisor = BigDecimal.ONE.subtract(totalPagamento);

            return valorEmprestimo.multiply(taxaMensal).divide(divisor, RoundingMode.HALF_UP);
        } catch (ArithmeticException e) {
            log.error("Erro ao calcular parcela mensal", e);
            throw new RuntimeException("Erro ao calcular parcela mensal", e);
        }
    }

    private void valicaoValoresParcelaMensal(BigDecimal valorEmprestimo, double taxaJurosAnual, int prazoMeses) {
        if (valorEmprestimo == null || valorEmprestimo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Valor do empréstimo", "O valor do empréstimo deve ser maior que zero.");
        }
        if (taxaJurosAnual <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Taxa de juros", "A taxa de juros anual deve ser maior que zero.");
        }
        if (prazoMeses <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Prazo de pagamento", "O prazo em meses deve ser maior que zero.");
        }
    }
}
