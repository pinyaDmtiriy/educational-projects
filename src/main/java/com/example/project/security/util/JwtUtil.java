package com.example.project.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim
                        ("roles",
                                user.getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .toList()
                        )
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

    public Set<GrantedAuthority> extractRoles(String token) {
         List<String> list = (List<String>) extractBody(token).get("roles");
         return list.stream()
                 .map(SimpleGrantedAuthority::new)
                 .collect(Collectors.toSet());
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
            throw e;
        }
    }

}
