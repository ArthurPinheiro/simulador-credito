package com.simulador.credito.infra.aws;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AwsConfigQueue {

    private final String queueName = "sqs-simulador-credito";
    private AmazonSQS sqs = AmazonSQSAsyncClientBuilder.defaultClient();

    public void awsConfigQueue() {
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName)
                .addAttributesEntry("", "").addAttributesEntry("", "");
        try {
            sqs.createQueue(createQueueRequest);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }
    }

    public void sendMensage(String mensagem) {
        var queueUrl = getQueueUrl(queueName);
        SendMessageRequest messageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody("Simulador de crédito")
                .withDelaySeconds(5);
        sqs.sendMessage(messageRequest);
    }

    private String getQueueUrl(String queueName) {
        return sqs.getQueueUrl(queueName).getQueueUrl();
    }

    public void getMessage() {
        var queueUrl = getQueueUrl(queueName);
        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
        messages.forEach(System.out::println);
    }
}
