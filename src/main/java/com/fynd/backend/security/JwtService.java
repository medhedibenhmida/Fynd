package com.fynd.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Clé secrète pour signer les tokens
    private static final String SECRET_KEY = "MaCleSecreteUltraLonguePourJWT1234567890!";

    // Durée de validité du token (ici 1 heure)
    private static final long EXPIRATION_MS = 1000 * 60 * 60;

    // Génère la clé de signature à partir de la chaîne secrète
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 🔹 Générer un token pour un utilisateur (ici on utilise le username/email)
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)               // l’identifiant principal dans le token
                .setIssuedAt(new Date())            // date de création
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // signature
                .compact();                        // retourne le token sous forme de chaîne
    }

    // 🔹 Valider un token (renvoie true si valide)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);  // parseClaimsJws lance une exception si invalide
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 🔹 Extraire le username/email du token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
