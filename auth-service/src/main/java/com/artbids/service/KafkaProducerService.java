package com.artbids.service;

import com.artbids.model.SendEmailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, SendEmailModel> kafkaTemplate;

    private final static String TOPIC_NAME = "send-email";

    public void sendMessage(SendEmailModel email) {
        kafkaTemplate.send(TOPIC_NAME, email);
    }

}
