package com.simulador.credito.infra.publish;

public class GeracaoPropostaPublish {

    /**
     * Classe responsável por enviar mensagem para uma fila.
     * A geração de proposta, em grande quantidade seria trata nesse publish.
     * A service se comunica com a GeracaoPropostaPublish e a partir daqui o fluxo continua
     */

    /**
    log.info("[ROTINA] Buscando URL da fila");
    GetQueueUrlResult queueUrlResult = amazonSQS.getQueueUrl(geracao-proposta-sqs);

    SendMessageRequest request = new SendMessageRequest()
            .withQueueUrl(queueUrlResult.getQueueUrl());

        log.info("[ROTINA] Enviando comando.");
        amazonSQS.sendMessage(request);
     **/
}
