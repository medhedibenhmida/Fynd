package com.fynd.backend.service;

import com.fynd.backend.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine; // ou spring5 si Spring Boot <3.0
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    // Méthode générique pour envoyer du HTML
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'envoi de l'email");
        }
    }

    // Méthode spécifique pour le reset password
    public void sendResetPasswordEmail(User user, String resetLink) {
        Context context = new Context();
        context.setVariable("resetLink", resetLink);

        // "reset-password" = fichier reset-password.html dans resources/templates
        String htmlContent = templateEngine.process("reset-password", context);
        sendHtmlEmail(user.getEmail(), "Réinitialisation de mot de passe", htmlContent);
    }
}
