package com.simulador.credito.presentation.rest.v1;

import com.simulador.credito.application.service.EmprestimoService;
import com.simulador.credito.application.service.EmprestimoServiceImpl;
import com.simulador.credito.presentation.rest.v1.controller.EmprestimoController;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoRequest;
import com.simulador.credito.presentation.rest.v1.dto.EmprestimoResponse;
import com.simulador.credito.utils.EmprestimoUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmprestimoController.class)
@MockitoSettings
class EmprestimoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmprestimoServiceImpl service;

    @Mock
    private EmprestimoService emprestimoService;

    private final String baseUrl = "/v1/emprestimos";

    private final EmprestimoController controller = new EmprestimoController(emprestimoService);

    @Test
    void getEmprestimo() throws Exception {
        var json = EmprestimoUtils.getJsonFromPath("src/test/resources/json/emprestimo-request.json");
        var request = EmprestimoUtils.getObjectFromJson(json, EmprestimoRequest.class);

        when(service.simularCredito(request)).thenReturn(getEmprestimoResponse());

        mockMvc.perform(post(baseUrl.concat("/simular"))
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testeDesempenhoSimulacoes() throws InterruptedException {
        // Configuração do teste
        int totalSimulacoes = 10_000; // Volume alto
        int threads = Runtime.getRuntime().availableProcessors(); // Quantidade de threads
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        List<Callable<Boolean>> tasks = getCallables(totalSimulacoes);

        // Medindo o tempo
        long inicio = System.currentTimeMillis();
        List<Future<Boolean>> resultados = executor.invokeAll(tasks);
        long fim = System.currentTimeMillis();

        executor.shutdown();

        // Validando os resultados
        assertTrue(resultados.stream().allMatch(Future::isDone), "Todas as simulações devem ser concluídas.");
        assertTrue(fim - inicio < 10000, "O tempo de execução deve ser menor que 10 segundos."); // Exemplo de limite
        System.out.println("Tempo total: " + (fim - inicio) + "ms para " + totalSimulacoes + " simulações.");
    }

    private List<Callable<Boolean>> getCallables(int totalSimulacoes) {
        List<Callable<Boolean>> tasks = new ArrayList<>();
        for (int i = 0; i < totalSimulacoes; i++) {
            EmprestimoRequest request = new EmprestimoRequest(
                    BigDecimal.valueOf(10_000 + i),
                    LocalDate.of(1990, 1, 1),
                    24
            );

            tasks.add(() -> {
                EmprestimoResponse response = controller.simularEmprestimo(request).getBody();
                // Verifique se o resultado é válido
                return response.valorTotal().compareTo(request.valorEmprestimo()) > 0;
            });
        }
        return tasks;
    }

    private EmprestimoResponse getEmprestimoResponse() {
        return new EmprestimoResponse(new BigDecimal("6613.70"), new BigDecimal("943.56"), new BigDecimal("56613.70"));
    }
}
