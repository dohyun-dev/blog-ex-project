package com.kwon.back.global.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    private static final long ACCESS_TOKEN_EXPIRATION_HOUR = 1;
    private static final long REFRESH_TOKEN_EXPIRATION_HOUR = 24;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String create(Long userId) {
        Date expiredDate = Date.from(Instant.now().plus(ACCESS_TOKEN_EXPIRATION_HOUR, ChronoUnit.HOURS));

        return Jwts.builder()
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();
    }

    public Long validate(String token) {
        Claims claims = null;

        try {
            // 토큰 만료시간 및 기타 검증
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return Long.valueOf(claims.getSubject());
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
