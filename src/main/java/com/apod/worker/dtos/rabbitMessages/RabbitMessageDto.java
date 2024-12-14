package com.apod.worker.dtos.rabbitMessages;

public record RabbitMessageDto(String to, String username, String token, String type) {
}
