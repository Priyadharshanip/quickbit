package com.quickbite.auth_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Generate a signing key from the secret string
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate a JWT token for a given email
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)              // who this token is for
                .claim("role", role)            // extra data inside token
                .setIssuedAt(new Date())        // when it was created
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // when it expires
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // sign it
                .compact();
    }

    // Extract email from a token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Check if token is expired
    public boolean isTokenValid(String token) {
        try {
            return !getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}