package com.github.gogoasac.gogger.output.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${logging.gogger.kafka.topic-name}")
    private String topicName;

    @Value("${logging.gogger.kafka.enabled}")
    private boolean kafkaEnabled;


    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        if (!kafkaEnabled) return;

        this.kafkaTemplate.send(topicName, message);
    }
}