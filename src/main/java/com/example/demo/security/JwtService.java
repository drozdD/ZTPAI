package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {
    // W produkcji klucz powinien być w application.properties
    private static final String SECRET = "bardzo_dlugi_i_bezpieczny_klucz_do_podpisywania_jwt_1234567890";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username, java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities) {
        String roles = authorities.stream()
                .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 godzina
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String extractRoles(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("roles", String.class);
    }
}