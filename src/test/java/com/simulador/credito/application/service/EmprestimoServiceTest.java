package com.simulador.credito.application.service;

import com.simulador.credito.presentation.rest.v1.dto.EmprestimoRequest;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@MockitoSettings
class EmprestimoServiceTest {

    @Autowired
    private EmprestimoServiceImpl service;

    @Test
    @DisplayName("Deve simular o crédito de empréstimo com idade menor que 25")
    void testSimularCreditoMenorQue25() {
        EmprestimoResponse response = service.simularCredito(getEmprestimoRequest(
                LocalDate.of(1999, 04, 19)));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.juros(), new BigDecimal("6613.70"));
        Assertions.assertEquals(response.valorMensal(), new BigDecimal("943.56"));
        Assertions.assertEquals(response.valorTotal(), new BigDecimal("56613.70"));
    }

    @Test
    @DisplayName("Deve simular o crédito de empréstimo com idade maior que 25")
    void testSimularCreditoEntre26Ate40() {
        EmprestimoResponse response = service.simularCredito(getEmprestimoRequest(
                LocalDate.of(1994, 01, 19)));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.juros(), new BigDecimal("3906.07"));
        Assertions.assertEquals(response.valorMensal(), new BigDecimal("898.43"));
        Assertions.assertEquals(response.valorTotal(), new BigDecimal("53906.07"));
    }

    @Test
    @DisplayName("Deve simular o crédito de empréstimo com idade maior que 40")
    void testSimularCreditoEntre40Ate60() {
        EmprestimoResponse response = service.simularCredito(getEmprestimoRequest(
                LocalDate.of(1984, 01, 19)));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.juros(), new BigDecimal("2583.28"));
        Assertions.assertEquals(response.valorMensal(), new BigDecimal("876.39"));
        Assertions.assertEquals(response.valorTotal(), new BigDecimal("52583.28"));
    }

    @Test
    @DisplayName("Deve simular o crédito de empréstimo com idade maior que 60")
    void testSimularCreditoMaiorQue60() {
        EmprestimoResponse response = service.simularCredito(getEmprestimoRequest(
                LocalDate.of(1964, 01, 19)));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.juros(), new BigDecimal("5249.57"));
        Assertions.assertEquals(response.valorMensal(), new BigDecimal("920.83"));
        Assertions.assertEquals(response.valorTotal(), new BigDecimal("55249.57"));
    }

    private EmprestimoRequest getEmprestimoRequest(LocalDate dataNascimento) {
        return new EmprestimoRequest(new BigDecimal("50000"), dataNascimento, 60);
    }
}
