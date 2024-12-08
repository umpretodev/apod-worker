package com.apod.worker.dtos.rabbitMessages;

public record TokenEmailRabbitDto(String to, String username, String token) {
}
