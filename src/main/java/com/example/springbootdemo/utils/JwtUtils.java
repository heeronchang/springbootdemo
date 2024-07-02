package com.example.springbootdemo.utils;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expireTime}")
    private long expireTime;

    private SecretKey key;
    private final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS512;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Map<String, Object> claims) {
        long currentMillis = DateUtil.current();

        String token = Jwts.builder()
                .claims(claims)
                .subject((String) claims.get("username"))
                .issuedAt(new Date(currentMillis))
                .expiration(new Date(currentMillis + expireTime))
                .signWith(key, ALGORITHM)
                .compact();
        return token;
    }

    public String createRefreshToken(Map<String, Object> claims) {
        long currentMillis = DateUtil.current();

        String token = Jwts.builder()
                .claims(claims)
                .subject((String) claims.get("username"))
                .issuedAt(new Date(currentMillis))
                .expiration(new Date(currentMillis + expireTime))
                .signWith(key, ALGORITHM)
                .compact();
        return token;
    }

    public Map<String, Object> parseToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("verify token error: {}", e.getMessage());
            return false;
        }
    }
}
