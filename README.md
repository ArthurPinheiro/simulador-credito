# Simulador de Crédito

## Descrição do Projeto

O projeto é dividido em uma arquitetura hexagonal, onde:
- **Infraestrutura:** Contém a comunicação externa, como o envio de e-mails e integrações.
- **Presentation:** Contém o controller responsável por expor os endpoints da aplicação.
- **Application:** Contém as regras de negócio, incluindo as services, factories e strategies.

A escolha dessa arquitetura visa:
- Suporte à expansão do projeto com adição de novas funcionalidades.
- Maior escalabilidade e flexibilidade.
- Desacoplamento entre os diferentes módulos.

## Requisitos
- **Java:** Versão 17
- **Maven:** Para gerenciar dependências

## Configuração do Ambiente
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/simulador-credito.git
   ```

2. Importe o projeto em sua IDE preferida (Eclipse, IntelliJ, etc.).

3. Execute a aplicação pela classe principal:
   `SimuladorCreditoApplication`.

   Ou use o Maven para executar o projeto diretamente:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints

### 1. Simulação de Empréstimo
**Descrição:** Simula um empréstimo baseado no valor solicitado, data de nascimento e prazo de pagamento.

**Endpoint:** `POST /v1/emprestimos/simular`

**Exemplo de Requisição:**
```bash
curl -X POST http://localhost:8080/v1/emprestimos/simular \
-H "Content-Type: application/json" \
-d '{
    "valorEmprestimo": 10000,
    "dataNascimento": "1990-05-15",
    "prazoMeses": 24
}'
```

**Exemplo de Resposta:**
```json
{
    "valorTotal": 12000,
    "valorParcelaMensal": 500,
    "totalJuros": 2000
}
```

### 2. Simulação de Múltiplos Empréstimos
**Descrição:** Simula múltiplos empréstimos em uma única requisição.

**Endpoint:** `POST /v1/emprestimos/simular-multiplos`

**Exemplo de Requisição:**
```bash
curl -X POST http://localhost:8080/v1/emprestimos/simular-multiplos \
-H "Content-Type: application/json" \
-d '[
    {
        "valorEmprestimo": 10000,
        "dataNascimento": "1990-05-15",
        "prazoMeses": 24
    },
    {
        "valorEmprestimo": 20000,
        "dataNascimento": "1985-07-20",
        "prazoMeses": 36
    }
]'
```

**Exemplo de Resposta:**
```json
[
    {
        "valorTotal": 12000,
        "valorParcelaMensal": 500,
        "totalJuros": 2000
    },
    {
        "valorTotal": 24000,
        "valorParcelaMensal": 666.67,
        "totalJuros": 4000
    }
]
```

### 3. Swagger
**Descrição:** Para acessar o swagger.

**Endpoint:** `GET http://localhost:8080/swagger-ui/index.html`

## Estrutura do Projeto
- **Infra:** Código relacionado à comunicação externa.
- **Presentation:** Camada que contém os controllers da aplicação.
- **Application:** Implementação das regras de negócio com foco em modularidade e expansibilidade.

## Decisões de Arquitetura
1. **Arquitetura Hexagonal:**
    - Facilita a expansão e manutenção do código.
    - Garante maior desacoplamento entre os componentes do sistema.
2. **Service e Strategy Pattern:**
    - Permitem que novas regras de negócio sejam adicionadas facilmente sem impactar outras partes do sistema.
