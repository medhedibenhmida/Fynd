package com.fynd.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bean pour encoder les mots de passe avec BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuration de la sécurité HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {}) // Active CORS en utilisant le bean corsConfigurationSource
                .csrf(AbstractHttpConfigurer::disable) // Désactive CSRF (utile pour Angular)
                .authorizeHttpRequests(auth -> auth
                        // Autorise register et login sans authentification
                        .requestMatchers("/users/register", "/users/login","/users/forgot-password","/users/reset-password").permitAll()
                        // Toutes les autres routes nécessitent une authentification
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    // Configuration CORS globale
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Frontend autorisé
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Méthodes HTTP autorisées
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Tous les headers sont autorisés (ex: Content-Type, Authorization)
        configuration.setAllowedHeaders(List.of("*"));

        // Permet l’envoi de cookies (si nécessaire)
        configuration.setAllowCredentials(true);

        // Appliquer la config à toutes les routes
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
