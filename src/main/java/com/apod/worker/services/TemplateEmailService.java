package com.apod.worker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class TemplateEmailService {
    @Autowired
    private RedisService redisService;

    private String readHtmlFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public String formatTokenEmailHtml(String token, String username) throws IOException {
        String htmlBody = readHtmlFile("src/main/resources/templates/TokenEmailTemplate.html");

        htmlBody = htmlBody.replace("$USERNAME$", username)
                            .replace("$TOKEN$", token);

        return htmlBody;
    }

    public String formatSubscriptionEmailHtml(String username) throws IOException {
        String htmlBody = readHtmlFile("src/main/resources/templates/SubscriptionEmailTemplate.html");

        htmlBody = htmlBody.replace("$TITLE$", redisService.get("apod_title"))
                            .replace("$DATE$", redisService.get("apod_date"))
                            .replace("$EXPLANATION$", redisService.get("apod_description"))
                            .replace("$PICTURE$", redisService.get("apod_url_image"))
                            .replace("$USERNAME$", username);

        return htmlBody;
    }
}
