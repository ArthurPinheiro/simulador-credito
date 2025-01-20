package com.simulador.credito.infra.adapter;

import com.simulador.credito.domain.model.Email;

public interface EmailAdapter {
    void enviar(Email email);
}
