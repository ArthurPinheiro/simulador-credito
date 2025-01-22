package com.simulador.credito.application.service;

import com.simulador.credito.application.exception.BusinessException;
import com.simulador.credito.application.factory.EmailFactory;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoRequest;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@MockitoSettings
class EmprestimoServiceTest {

    @Autowired
    private EmprestimoServiceImpl service;

    @Mock
    private EmailFactory emailFactory;

    @Test
    @DisplayName("Deve simular o crédito de empréstimo com idade menor que 25")
    void testSimularCreditoMenorQue25() {

        emailFactory();
        EmprestimoResponse response = service.simularCredito(getEmprestimoRequest(
                LocalDate.of(1999, 4, 19)));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.juros(), new BigDecimal("6613.70"));
        Assertions.assertEquals(response.valorMensal(), new BigDecimal("943.56"));
        Assertions.assertEquals(response.valorTotal(), new BigDecimal("56613.70"));
        verify(emailFactory, times(1)).enviarEmail(getEmprestimoResponse());
    }

    @Test
    @DisplayName("Deve simular o crédito de empréstimo com idade maior que 25")
    void testSimularCreditoEntre26Ate40() {
        emailFactory();
        EmprestimoResponse response = service.simularCredito(getEmprestimoRequest(
                LocalDate.of(1994, 1, 19)));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.juros(), new BigDecimal("3906.07"));
        Assertions.assertEquals(response.valorMensal(), new BigDecimal("898.43"));
        Assertions.assertEquals(response.valorTotal(), new BigDecimal("53906.07"));
    }

    @Test
    @DisplayName("Deve simular o crédito de empréstimo com idade maior que 40")
    void testSimularCreditoEntre40Ate60() {
        emailFactory();
        EmprestimoResponse response = service.simularCredito(getEmprestimoRequest(
                LocalDate.of(1984, 1, 19)));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.juros(), new BigDecimal("2583.28"));
        Assertions.assertEquals(response.valorMensal(), new BigDecimal("876.39"));
        Assertions.assertEquals(response.valorTotal(), new BigDecimal("52583.28"));
    }

    @Test
    @DisplayName("Deve simular o crédito de empréstimo com idade maior que 60")
    void testSimularCreditoMaiorQue60() {
        emailFactory();
        EmprestimoResponse response = service.simularCredito(getEmprestimoRequest(
                LocalDate.of(1964, 1, 19)));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.juros(), new BigDecimal("5249.57"));
        Assertions.assertEquals(response.valorMensal(), new BigDecimal("920.83"));
        Assertions.assertEquals(response.valorTotal(), new BigDecimal("55249.57"));
    }

    @Test
    @DisplayName("Deve retornar erro. Valor empréstimo menor que 0")
    void testSimularCreditoMenorQue0() {
        var request = new EmprestimoRequest(new BigDecimal("-50000"), LocalDate.of(1999, 1, 26), 60);
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> service.simularCredito(request));
        Assertions.assertEquals(businessException.getStatusCode(), HttpStatus.BAD_REQUEST.value());
        Assertions.assertEquals(businessException.getCampo(), "Valor do empréstimo");
    }

    @Test
    @DisplayName("Deve retornar erro. Prazo de pagamento igual a 0")
    void testSimularCreditoComPrazoPagamentoIgual0() {
        var request = new EmprestimoRequest(new BigDecimal("50000"), LocalDate.of(1999, 1, 26), 0);
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> service.simularCredito(request));
        Assertions.assertEquals(businessException.getStatusCode(), HttpStatus.BAD_REQUEST.value());
        Assertions.assertEquals(businessException.getCampo(), "Prazo de pagamento");
    }

    private EmprestimoRequest getEmprestimoRequest(LocalDate dataNascimento) {
        return new EmprestimoRequest(new BigDecimal("50000"), dataNascimento, 60);
    }

    private EmprestimoResponse getEmprestimoResponse() {
        return new EmprestimoResponse(new BigDecimal("6613.70"),
                new BigDecimal("943.56"),new BigDecimal("56613.70")
        );
    }

    private void emailFactory() {
        doNothing().when(emailFactory).enviarEmail(getEmprestimoResponse());
        emailFactory.enviarEmail(getEmprestimoResponse());
    }
}
