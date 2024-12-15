package com.apod.worker.services;

import com.apod.worker.dtos.rabbitMessages.RabbitMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@EnableRabbit
public class RabbitService {
    @Autowired
    private EmailService emailService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = {"email_queue"})
    public void receiveMessage (@Payload String message)  {
        try {
            log.info("message received: {}", message);

            RabbitMessageDto rabbitMessageDto = objectMapper.readValue(message, RabbitMessageDto.class);
            var typeMessage = rabbitMessageDto.type();

            if (typeMessage.equals("token")) { sendtokenValidationEmail(rabbitMessageDto);}
            if (typeMessage.equals("subscription")) { sendApodUpdateEmail(rabbitMessageDto);}
        }

        catch (Exception error) {
            log.error(error.getMessage(), error);
        }
    }

    private void sendtokenValidationEmail(RabbitMessageDto rabbitMessageDto) throws InterruptedException, IOException, MessagingException {
        log.info("Tokens message received");

        emailService.sendToken(rabbitMessageDto.to(), rabbitMessageDto.token(), rabbitMessageDto.username());
        System.out.println(">>>>>>> Tokens message received" + rabbitMessageDto);

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
    }

    private void sendApodUpdateEmail(RabbitMessageDto rabbitMessageDto) throws InterruptedException, IOException, MessagingException {
        log.info(">>>>>>> Subscription message received" + rabbitMessageDto);

        emailService.sendSubscription(
                rabbitMessageDto.to(),
                rabbitMessageDto.username()
        );

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
    }
}
