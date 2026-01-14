package com.fynd.backend.controller;

import com.fynd.backend.dto.*;
import com.fynd.backend.entities.User;
import com.fynd.backend.repository.UserRepository;
import com.fynd.backend.security.JwtService;
import com.fynd.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtService jwtService,AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authService = authService;
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if(optionalUser.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponse(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // On enveloppe dans try/catch pour ne pas exposer si email existe ou non
        try {
            authService.createPasswordResetToken(email);
        } catch (RuntimeException e) {
            // Ne rien faire, on ne veut pas divulguer si l'email est valide
        }

        return ResponseEntity.ok("Si un compte existe avec cet email, un email vous sera envoyé");
    }
}
