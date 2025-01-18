package com.example.s_and_c.Service.Impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "questa_è_una_chiave_super_segreta_e_lunga_abbastanza!";
    public String extractEmail(String jwtToken) {
        return extractClaims(jwtToken, Claims::getSubject);
    }

    private Key getSignInKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        // Assicurati che la chiave sia della lunghezza corretta per HS256
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        System.out.println("Generating token");
        System.out.println("Username: " + userDetails.getUsername());
        System.out.println("Auth: " + userDetails.getAuthorities());
        return generateToken(new HashMap<>(), userDetails);
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Aggiungi il ruolo alle claims
        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        System.out.println(userDetails.getAuthorities());
        System.out.println(userDetails.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String email = extractEmail(jwtToken);
        return (userDetails.getUsername().equals(email)) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }
    private Date extractExpiration(String jwtToken) {
        return extractClaims(jwtToken, Claims::getExpiration);
    }

    public <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }
}
