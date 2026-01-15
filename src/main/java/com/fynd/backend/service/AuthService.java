package com.fynd.backend.service;

import com.fynd.backend.entities.PasswordResetToken;
import com.fynd.backend.entities.User;
import com.fynd.backend.repository.PasswordResetTokenRepository;
import com.fynd.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public void createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No user found with email " + email));

        String token = java.util.UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:4200/reset-password?token=" + token;

        emailService.sendResetPasswordEmail(user, resetLink);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        if (resetToken.isUsed()) {
            throw new RuntimeException("Token déjà utilisé");
        }
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
             throw new RuntimeException("Token expiré");
            }
        User user = resetToken.getUser();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        }
    }
