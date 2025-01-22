package com.simulador.credito.application.factory;

import com.simulador.credito.domain.model.Email;
import com.simulador.credito.infra.adapter.EmailAdapter;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoResponse;
import org.springframework.stereotype.Component;

@Component
public class EmailFactory {

    private final EmailAdapter emailAdapter;

    public EmailFactory(EmailAdapter emailAdapter) {
        this.emailAdapter = emailAdapter;
    }

    public void enviarEmail(EmprestimoResponse response) {
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
