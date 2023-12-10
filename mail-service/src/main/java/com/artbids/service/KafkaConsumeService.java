package com.artbids.service;

import com.artbids.model.SendEmailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumeService {

    private final EmailService emailService;

    @KafkaListener(topics = "send-email", groupId = "consume-id",containerFactory = "emailListener")
    public void consume(SendEmailModel model) {
        emailService.sendEmail(model);
        System.out.println("selammmm");
    }

}
