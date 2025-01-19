package org.invest.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String secretKey;
    private final Duration accessDuration = Duration.ofDays(1);
    private final Duration refreshDuration = Duration.ofDays(7);

    public String generateAccessToken(Long userId, String email) {
        return Jwts.builder()
                .claims(getClaims(userId))
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessDuration.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(Long userId, String email) {
        return Jwts.builder()
                .claims(getClaims(userId))
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshDuration.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean IsValidToken(String token) {
        try{
            validateToken(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String refreshAccessToken(String refreshToken) {
        Claims claims = validateToken(refreshToken);
        String email = claims.getSubject();
        Long userId = claims.get("id", Long.class);
        return generateAccessToken(userId, email);
    }

    private SecretKey getSigningKey(){
        byte[] keysBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keysBytes);
    }

    private HashMap<String, Object> getClaims(Long userId) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        return claims;
    }


}
