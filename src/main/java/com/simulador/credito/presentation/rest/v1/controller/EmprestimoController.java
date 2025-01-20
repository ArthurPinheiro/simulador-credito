package com.simulador.credito.presentation.rest.v1.controller;

import com.simulador.credito.application.service.EmprestimoService;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoRequest;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
public class EmprestimoController implements EmprestimoOperations {

    private final EmprestimoService service;
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<EmprestimoResponse> simularEmprestimo(EmprestimoRequest emprestimoRequest) {
        var emprestimoResponse = service.simularCredito(emprestimoRequest);
        return ResponseEntity.ok(emprestimoResponse);
    }

    @Override
    public List<EmprestimoResponse> simularEmprestimos(List<EmprestimoRequest> requests) {
        List<CompletableFuture<EmprestimoResponse>> futures = requests.stream()
                .map(request -> CompletableFuture.supplyAsync(() -> service.simularCredito(request), executorService))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
