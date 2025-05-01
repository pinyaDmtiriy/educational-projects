package com.example.project.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String key;
    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getKey() {
        byte[] arr = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(arr);
    }

    public String getToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("roles", user.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    private Claims extractBody(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractRole(String token) {
        return extractBody(token).get("roles").toString();
    }

    public String extractSubject(String token) {
        return extractBody(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractBody(token).getExpiration();
    }

    public Date extractIssuedAt(String token) {
        return extractBody(token).getIssuedAt();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
