package com.simulador.credito.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final Integer statusCode;
    private final String campo;
    private final String detalhe;
}
