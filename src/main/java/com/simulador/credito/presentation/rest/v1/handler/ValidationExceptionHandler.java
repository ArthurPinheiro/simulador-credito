package com.simulador.credito.presentation.rest.v1.handler;

import com.simulador.credito.application.exception.BusinessException;
import com.simulador.credito.presentation.rest.v1.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        var error = new ErrorResponse(ex.getStatusCode(), ex.getCampo(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }
}
