package com.apod.worker.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {
    @RabbitListener(queues = {"tokens"})
    public void receive(@Payload String message) {
        System.out.println(message);
    }
}
