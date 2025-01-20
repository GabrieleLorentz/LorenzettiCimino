/*package com.example.s_and_c.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component  // Aggiungi questa annotazione
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .setSubject(email)  // usa setSubject invece di subject
                .setIssuedAt(currentDate)  // usa setIssuedAt invece di issuedAt
                .setExpiration(expireDate)  // usa setExpiration invece di expiration
                .signWith(key(), SignatureAlgorithm.HS256)  // specifica l'algoritmo
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()  // usa parserBuilder invece di parser
                .setSigningKey(key())         // usa setSigningKey invece di verifyWith
                .build()
                .parseClaimsJws(token)        // usa parseClaimsJws invece di parseSignedClaims
                .getBody();                   // usa getBody invece di getPayload

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}*/