package com.apod.worker.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEmailService templateEmailService;

    private String subject = "Fotos Astronômicas do Dia";

    void sendToken(String to, String token, String username) throws MessagingException, IOException {
        log.info("Send token email to: {}", to);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(templateEmailService.formatTokenEmailHtml(token, username), true);

        javaMailSender.send(message);
    }

    void sendSubscription(String to, String username) throws MessagingException, IOException {
        log.info("Send subscription email to: {}", to);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new  MimeMessageHelper(message, true);

        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(templateEmailService.formatSubscriptionEmailHtml(username), true);

        javaMailSender.send(message);
    }
}
