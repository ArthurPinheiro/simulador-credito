package com.simulador.credito.presentation.rest.v1.commons;

import java.util.List;

public class ResponseApiErro {
    private final List<ResponseErrorItem> erros;

    public ResponseApiErro(List<ResponseErrorItem> errors) {
        this.erros = errors;
    }

    public List<ResponseErrorItem> getErros() {
        return erros;
    }

    public static class ResponseErrorItem {

        private String campo;
        private List<String> mensagens;

        public String getCampo() {
            return campo;
        }

        public List<String> getMensagens() {
            return mensagens;
        }


    }
}
