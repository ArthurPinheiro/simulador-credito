package com.simulador.credito.infra.adapter;

import com.simulador.credito.domain.model.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailAdapterImpl implements EmailAdapter{

    private final JavaMailSender mailSender;

    public EmailAdapterImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviar(Email email) {
        var message = new SimpleMailMessage();
        message.setFrom("noreply@email.com");
        message.setTo(email.to());
        message.setSubject(email.subject());
        message.setText(email.body());

        mailSender.send(message);
    }
}
