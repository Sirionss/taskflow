package com.taskflow.taskflow.security;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final long EXPIRATION = 86400000;

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token){
        try{
            extractUsername(token);
            return true;
        } catch (Exception e){
            return false;
        }

    }

}
