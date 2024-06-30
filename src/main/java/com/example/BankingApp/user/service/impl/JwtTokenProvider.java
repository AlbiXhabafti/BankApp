package com.example.BankingApp.user.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtTokenProvider {

    @Value("${app-jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    private final Map<String, String> revokedTokens = new ConcurrentHashMap<>();

    public String generateToken(Authentication authentication){

        String email = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(generateKey())
                .compact();

        return token;
    }

    private Key generateKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get email from JWT token
    public String getUserEmail(String token){

        return Jwts.parser()
                .verifyWith((SecretKey) generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token){
            Jwts.parser()
                    .verifyWith((SecretKey) generateKey())
                    .build()
                    .parse(token);
            return true;

    }

    public void revokeToken(String principal, String token) {
        revokedTokens.put(principal, token);
    }
    public boolean isTokenRevoked(String principal, String token) {
        String storedToken = revokedTokens.get(principal);
        return storedToken != null && storedToken.equals(token);
    }
    public void removeRevokedToken(String principal) {
        revokedTokens.remove(principal);
    }
}