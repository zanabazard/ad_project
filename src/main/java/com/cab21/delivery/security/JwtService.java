package com.cab21.delivery.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.security.Key;
import java.util.function.Function;
@Component
public class JwtService {
    private final Key key;
    private final long expirationMs;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes()); // >= 32 bytes
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username, Map<String, Object> extra) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claims(extra)
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMs))
                .signWith(key)                // 0.12.x: no algorithm arg needed for HMAC
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()     // 0.12.x parse API
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

    public boolean isTokenValid(String token, String username) {
        try {
            return username.equals(extractUsername(token)) &&
                    extractClaim(token, Claims::getExpiration).after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}

