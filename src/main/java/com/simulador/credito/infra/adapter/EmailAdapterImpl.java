package com.simulador.credito.infra.adapter;

import com.simulador.credito.domain.model.Email;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EmailAdapterImpl implements EmailAdapter{

    private final JavaMailSender mailSender;

    public EmailAdapterImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviar(Email email) {
        log.info("Enviando e-mail");
        var message = new SimpleMailMessage();
        message.setFrom("noreply@email.com");
        message.setTo(email.to());
        message.setSubject(email.subject());
        message.setText(email.body());

        mailSender.send(message);
        log.info("Email enviado com sucesso");
    }
}
