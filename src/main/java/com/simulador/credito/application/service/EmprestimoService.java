package com.simulador.credito.application.service;

import com.simulador.credito.presentation.rest.v1.dto.EmprestimoRequest;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoResponse;

public interface EmprestimoService {

    EmprestimoResponse simularCredito(EmprestimoRequest request);
}
