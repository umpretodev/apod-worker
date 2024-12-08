package com.apod.worker.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class TemplateEmailService {
    private String readHtmlFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public String formatTokenEmailHtml(String token, String name) throws IOException {
        String htmlBody = readHtmlFile("src/main/resources/templates/TokenEmailTemplate.html");

        htmlBody = htmlBody.replace("$USERNAME$", name);
        htmlBody = htmlBody.replace("$TOKEN$", token);

        return htmlBody;
    }

    public String formatSubscriptionEmailHtml() {
        return "<h1>Oi</h1>";
    }
}
