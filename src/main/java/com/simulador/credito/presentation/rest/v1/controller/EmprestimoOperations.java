package com.simulador.credito.presentation.rest.v1.controller;

import com.simulador.credito.presentation.rest.v1.commons.ResponseApiErro;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoRequest;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("v1/emprestimos")
public interface EmprestimoOperations {
    @Operation(
            summary = "Simulador de crédito",
            description = "Simulador de crédito que permite o cliente simular empréstimo "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseApiErro.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Erro interno de Servidor", content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseApiErro.class))
                    })
    })
    @PostMapping("/simular")
    ResponseEntity<EmprestimoResponse> simularEmprestimo(@RequestBody EmprestimoRequest emprestimoRequest);

    @Operation(
            summary = "Simulador de crédito",
            description = "Simulador de crédito que recebe uma alta volumetria de requisições "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseApiErro.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Erro interno de Servidor", content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseApiErro.class))
                    })
    })
    @PostMapping("/simular-multiplos")
    List<EmprestimoResponse> simularEmprestimos(@RequestBody List<EmprestimoRequest> requests);
}
