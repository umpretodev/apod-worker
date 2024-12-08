package com.apod.worker.dtos.rabbitMessages;

public record TokenMessageDto(String email, String name, String token) {
}
