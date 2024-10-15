package com.example.planboardbackend.kafka.service;

import com.example.planboardbackend.kafka.dto.EmailSendingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.planboardbackend.kafka.config.KafkaTopicConfig.emailSendingTask;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, EmailSendingDto> kafkaTemplate;

    public void sendRegisterEmailMessage(EmailSendingDto emailSendingDto) {
        kafkaTemplate.send(emailSendingTask, emailSendingDto);
    }

}