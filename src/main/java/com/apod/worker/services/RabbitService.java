package com.apod.worker.services;

import com.apod.worker.dtos.rabbitMessages.TokenMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RabbitService {
    @Autowired
    private EmailService emailService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = {"tokens"})
    public void receiveTokens(@Payload String message) throws InterruptedException, IOException, MessagingException {
        log.info("Message received {}", message);

        TokenMessageDto tokenMessageDto = objectMapper.readValue(message, TokenMessageDto.class);
        emailService.sendToken(tokenMessageDto.email(), tokenMessageDto.token(), tokenMessageDto.name());

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
    }
}
